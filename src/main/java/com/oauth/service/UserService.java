package com.oauth.service;

import com.oauth.data.entity.OpenUser;
import com.oauth.data.entity.User;
import com.oauth.data.entity.UserLoginCode;
import com.oauth.util.MD5Util;
import com.oauth.util.MapBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yjdini on 2017/8/20.
 *
 */
@Component
public class UserService extends BaseService {
    public Map verifyUser(String account, String password) {
        User user = userRepository.findByAccountAndPassword(account, password);
        if (user == null)
            return MapBuilder.error("账号或密码错误").getMap();
        else
            return MapBuilder.ok().put("result", user).getMap();
    }

    public Map clearLoginCode(String code) {
        userLoginCodeRepository.delete(code);
        return MapBuilder.ok().getMap();
    }

    public Integer getUserIdByLoginCode(String loginCode) {
        UserLoginCode userLoginCode = userLoginCodeRepository.findOne(loginCode);
        return userLoginCode == null ? null : userLoginCode.getUserId();
    }

    Map getUserInfoByOpenId(Integer openId) {
        Integer userId = openUserRepository.findOne(openId).getUserId();
        User user = userRepository.findOne(userId);
        Map result = new HashMap();
        result.put("user", user);
        return result;
    }

    Integer getOpenId(String clientId, Integer userId) {
        List<OpenUser> openUsers = openUserRepository.findByClientIdAndUserId(clientId, userId);
        if ( openUsers == null || openUsers.size() == 0) {
            OpenUser openUser = new OpenUser();
            openUser.setClientId(clientId);
            openUser.setUserId(userId);
            return openUserRepository.save(openUser).getOpenId();
        }
        return openUsers.get(0).getOpenId();
    }

    public void saveLoginCode(Integer uid, String code) {
        UserLoginCode loginCode = new UserLoginCode(code, uid);
        userLoginCodeRepository.save(loginCode);
    }

    public String generateLoginCode(User user) {
        return MD5Util.MD5(String.valueOf(System.currentTimeMillis() + user.getId() + user.getAccount()));
    }
}
