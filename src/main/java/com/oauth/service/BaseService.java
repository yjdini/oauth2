package com.oauth.service;

import com.oauth.data.jpa.OpenUserRepository;
import com.oauth.data.jpa.RegistedClientRepository;
import com.oauth.data.jpa.UserLoginCodeRepository;
import com.oauth.data.jpa.UserRepository;
import com.oauth.util.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yjdini on 2017/8/16.
 *
 */
public class BaseService {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected OpenUserRepository openUserRepository;
    @Autowired
    protected UserLoginCodeRepository userLoginCodeRepository;
    @Autowired
    protected RegistedClientRepository registedClientRepository;

    protected AESUtil aesUtil = AESUtil.getInstance();
}
