package com.ini.service;

import com.ini.data.jpa.OpenUserRepository;
import com.ini.data.jpa.RegistedClientRepository;
import com.ini.data.jpa.UserLoginCodeRepository;
import com.ini.data.jpa.UserRepository;
import com.ini.util.AESUtil;
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
