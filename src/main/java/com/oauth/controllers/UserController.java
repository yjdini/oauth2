package com.oauth.controllers;

import com.oauth.data.entity.User;
import com.oauth.service.UserService;
import com.oauth.service.client.OAuthClient;
import com.oauth.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by yjdini on 2017/8/20.
 *
 */

@RequestMapping("/api/usermanage/")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private OAuthClient oAuthClient;

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map login (@RequestBody Map<String, Object> body, HttpServletResponse response) {
        String account  = (String) body.get("account");
        String password = (String) body.get("password");
        Map result = userService.verifyUser(account, password);
        if ("ok".equals(result.get("status"))) {
            String code = userService.generateLoginCode(((User)result.get("result")));
            userService.saveLoginCode(((User)result.get("result")).getId(), code);
            response.addCookie(new Cookie("code", code));
        }
        return result;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Map logout (HttpServletRequest request) {
        String code = OAuthController.getLoginCode(request);
        return userService.clearLoginCode(code);
    }

    @RequestMapping(value = "/open/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map loginWithOpenCode (@RequestBody Map<String, Object> body, HttpServletResponse response) {
        String openCode  = (String) body.get("code");
        Map result = oAuthClient.loginWithOpenCode(openCode);
        String loginCode = (String) result.get("loginCode");
        response.addCookie(new Cookie("code", loginCode));

        return result;
    }


}
