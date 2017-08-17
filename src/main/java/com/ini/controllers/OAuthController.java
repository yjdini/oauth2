package com.ini.controllers;

import com.ini.service.OAuthService;
import com.ini.util.MapBuilder;
import com.ini.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by yjdini on 2017/8/14.
 *
 */

@RequestMapping("/api/oauth2/")
@RestController
public class OAuthController {

    private final OAuthService oAuthService;

    @RequestMapping(value = "/authorize")
    public String authorize (HttpServletRequest request, HttpServletResponse response) {
        String client_id = request.getParameter("client_id");
        String redirect_uri = request.getParameter("redirect_uri");
        String code = getLoginCode(request);

        if ( StringUtil.isBlank(client_id) || StringUtil.isBlank(redirect_uri) || StringUtil.isBlank(code) )
            return "缺少参数";

        return oAuthService.authorize(client_id, redirect_uri, code, response);

    }

    @RequestMapping(value = "/access_token", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map access_token (HttpServletRequest request) {
        String client_secret = request.getParameter("client_secret");
        String client_id = request.getParameter("client_id");
        String outCode = request.getParameter("code");

        if ( StringUtil.isBlank(client_id) || StringUtil.isBlank(client_secret) || StringUtil.isBlank(outCode) )
            return MapBuilder.error("缺少参数").getMap();

        return oAuthService.getAccessToken(client_id, client_secret, outCode);
    }

    @RequestMapping(value = "/get_token_info")
    public Map get_token_info (HttpServletRequest request, HttpServletResponse response) {
        String access_token = request.getParameter("access_token");
        if ( StringUtil.isBlank(access_token))
            return MapBuilder.error("参数错误").getMap();
        return oAuthService.getTokenInfo(access_token);
    }

    @RequestMapping(value = "/revokeoauth2")
    public Map revokeoauth2 (HttpServletRequest request, HttpServletResponse response) {
        String access_token = request.getParameter("access_token");
        if ( StringUtil.isBlank(access_token))
            return MapBuilder.error("参数错误").getMap();
        return oAuthService.revokeOauth2(access_token);
    }

    @RequestMapping(value = "/userinfo")
    public Map userInfo (HttpServletRequest request, HttpServletResponse response) {
        String access_token = request.getParameter("access_token");
        if ( StringUtil.isBlank(access_token))
            return MapBuilder.error("参数错误").getMap();
        return oAuthService.userInfo(access_token);
    }

    private String getLoginCode(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String code = null;
        if (cookies == null)
            return null;
        for (Cookie cookie : cookies){
            if ( "code".equals(cookie.getName()) ) {
                return cookie.getValue();
            }
        }
        return null;
    }

    @Autowired
    public OAuthController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }
}
