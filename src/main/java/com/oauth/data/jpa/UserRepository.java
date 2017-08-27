package com.oauth.data.jpa;

import com.oauth.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * Created by yjdini on 2017/8/15.
 *
 */
public interface UserRepository extends JpaRepository<User, Integer>, QueryByExampleExecutor<User> {
    User findByAccountAndPassword(String account, String password);

    User findByOpenId(String openId);
}
