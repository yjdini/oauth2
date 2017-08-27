package com.oauth.service.client;

import com.oauth.data.entity.User;
import com.oauth.data.entity.UserLoginCode;
import com.oauth.data.jpa.UserLoginCodeRepository;
import com.oauth.service.BaseService;
import com.oauth.service.UserService;
import com.oauth.util.HttpUtil;
import com.oauth.util.MapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by yjdini on 2017/8/21.
 *
 */
@Component
public class OAuthClientImpl extends BaseService implements OAuthClient{
    @Autowired
    private UserService userService;

    private final static String getTokenUrl = "";
    private final static String getTokenParam = "id={id}&secret={secret}&code={code}";

    private final static String getUserUrl = "";
    private final static String getUserParam = "token={token}";


    @Override
    public Map loginWithOpenCode(String code) {
        String token = getTokenWithCode(code);
        User user = getUserWithToken(token);
        String loginCode = userService.generateLoginCode(user);
        UserLoginCode userLoginCode = new UserLoginCode(loginCode, user.getId());
        userLoginCodeRepository.save(userLoginCode);
        return MapBuilder.ok().put("user", user).put("loginCode", loginCode).getMap();
    }

    private String getTokenWithCode(String code) {
        String resp = HttpUtil.sendPost(getTokenUrl, getTokenParam.replace("{code}", code), null);
        return null;
    }

    private User getUserWithToken(String token) {
        String resp = HttpUtil.sendPost(getUserUrl, getUserParam.replace("{token}", token), null);
        String openId = null;
        User user = userRepository.findByOpenId(openId);
        if ( user == null ) {
            user = new User();
            user.setOpenId(openId);
        }
        return user;
    }
}
