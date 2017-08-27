package com.oauth.service.client;

import com.oauth.data.entity.User;

import java.util.Map;

/**
 * Created by yjdini on 2017/8/21.
 *
 */

public interface OAuthClient {
    Map loginWithOpenCode(String code);
}
