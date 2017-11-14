package com.oauth.service;

import com.oauth.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yjdini on 2017/11/15.
 *
 */
public class KuwoService extends BaseService {
    private static final String kuwoClientId = "123";

    private static final String kuwoLoginUrl = "http://60.29.226.11/KuwoLivePhone/thirduser/login?accessToken=" +
            "{accessToken}&mobileNo={clientId}&thirdtype=flsh";

    @Autowired
    private OAuthService oAuthService;
    @Autowired
    private UserService userService;


    public Map<String, String> getKuwoInfo(Integer userId) {
        Integer openId = userService.getOpenId(kuwoClientId, userId);
        // 先生成&保存accessToken，然后去调用kw的接口
        String accessToken = oAuthService.storageAccessToken(kuwoClientId, userId, openId);
        return getKuwoInfoByHttp(accessToken, kuwoClientId);
    }

    private Map<String,String> getKuwoInfoByHttp(String accessToken, String kuwoClientId) {
        String url = kuwoLoginUrl.replace("{accessToken}", accessToken)
                .replace("{clientId}", kuwoClientId);
        String resp = HttpUtil.get(url);
        
        return getUidSidFromKwResp(resp);
    }

    private Map<String,String> getUidSidFromKwResp(String resp) {
        JSONObject jsobj = JSONObject.fromObject(resp);
        Map result = new HashMap<String, String>();
        return result;
    }
}
