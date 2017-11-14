package com.oauth.service;

import com.oauth.data.entity.RegistedClient;
import com.oauth.util.MD5Util;
import com.oauth.util.MapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by yjdini on 2017/8/15.
 *
 */
@Component
public class OAuthService extends BaseService {
    private static final int expiresIn = 60*60;
    private static final String[] gcList = new String[expiresIn];//回收tokenMap中的过期token
    private static int gcIndex = 0;

    private static Map<String, Map> tokenMap = new HashMap<String, Map>();//accessToken => tokenInfo

    private static Timer gcTimer;

    static {
        for (int i = 0; i < expiresIn; i ++) {
            gcList[i] = "";
        }
        if (gcTimer == null) {
            gcTimer = new Timer();
            gcTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    String tokensStr = gcList[gcIndex];
                    if (tokensStr == null)
                        return;
                    String[] tokens = tokensStr.split("#");
                    if (tokens.length != 0) {
                        for (String token : tokens) {
                            if (!"".equals(token)) {
                                tokenMap.remove(token);
                            }
                        }
                    }
                    gcList[gcIndex] = "";
                    gcIndex = (gcIndex + 1) % expiresIn;
                }
            }, 0, 1000);
        }

    }

    public String authorize(String client_id, String redirect_uri, String loginCode, HttpServletResponse response) {
        if ( verifyClientHosts(client_id, redirect_uri) ) {
            return "client_app错误或重定向url未配置";
        }
        if ( userService.getUserIdByLoginCode(loginCode) != null ) {
            String innerCode = loginCode + "#" + client_id + "#" +System.currentTimeMillis()%10000;
            String outCode = aesUtil.encrypt(innerCode);
            try {
                response.sendRedirect(redirect_uri+"&code="+ URLEncoder.encode(outCode, "utf-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("=======>code:"+outCode);
            return "";
        }
        return "用户未登录";
    }

    public Map getAccessToken(String client_id, String client_secret, String outCode) {
        if ( !verifyClientSecret(client_id, client_secret) ) {
            return MapBuilder.error("用户名、密码错误").getMap();
        }
        String innerCode = aesUtil.decrypt(outCode);

        String[] info = innerCode.split("#");
        if ( info.length != 3 )
            return MapBuilder.error("code错误").getMap();
        String loginCode = info[0];
        String clientId = info[1];

        if ( !client_id.equals(clientId) )
            return MapBuilder.error("code错误").getMap();

        Integer userId = userService.getUserIdByLoginCode(loginCode);

        if ( userId == null )
            return MapBuilder.instance().put("status","unlogin")
                    .setMessage("code过期").getMap();

        Integer openId = userService.getOpenId(clientId, userId);
        String accessToken = storageAccessToken(clientId, userId, openId);

        Map<String, Object> result = new HashMap<>();
        result.put("access_token", accessToken);
        result.put("expires_in", expiresIn);
        result.put("uid", openId);
        return MapBuilder.ok().put("result", result).getMap();
    }

    public String storageAccessToken(String clientId, Integer userId, Integer openId) {
        String accessToken = generateAccessToken(userId, clientId);
        Map tokenInfo = MapBuilder.instance().put("uid", openId)
                .put("client_id", clientId)
                .put("create_at", System.currentTimeMillis()/1000)
                .getMap();
        tokenMap.put(accessToken, tokenInfo);
        addToGclist(accessToken);
        return accessToken;
    }

    public Map getTokenInfo(String access_token) {
        Map info = tokenMap.get(access_token);
        if (info == null)
            return MapBuilder.error("token错误或已过期").getMap();
        info.put("expires_in", (long) info.get("create_at") + expiresIn - System.currentTimeMillis()/1000);
        return MapBuilder.ok().put("result", info).getMap();
    }

    public Map revokeOauth2(String access_token) {
        tokenMap.remove(access_token);
        return MapBuilder.ok().getMap();
    }

    public Map userInfo(String access_token) {
        Map info = tokenMap.get(access_token);
        if (info == null)
            return MapBuilder.error("token错误或已过期").getMap();
        Integer openId = (Integer) info.get("uid");
        Map user = userService.getUserInfoByOpenId(openId);
        return MapBuilder.ok().put("result", user).getMap();
    }

    private void addToGclist(String accessToken) {
        int to = (gcIndex + expiresIn - 1) % expiresIn;
        gcList[to] = gcList[to]+"#"+accessToken;
    }

    private String generateAccessToken(Integer userId, String clientId) {
        return MD5Util.MD5(userId + System.currentTimeMillis() + clientId);
    }

    private boolean verifyClientHosts(String client_id, String redirect_uri) {
        RegistedClient registedClient = registedClientRepository.findByClientId(client_id).get(0);
        if (registedClient == null)
            return false;
        String hosts = registedClient.getAllowedHosts();
        for (String host : hosts.split(",")) {
            if (!redirect_uri.contains(host))
                return true;
        }
        return false;
    }

    private boolean verifyClientSecret(String client_id, String client_secret) {
        RegistedClient registedClient = registedClientRepository.findByClientId(client_id).get(0);
        return !(registedClient == null || !registedClient.getClientSecret().equals(client_secret));
    }

    @Autowired
    private UserService userService;
}
