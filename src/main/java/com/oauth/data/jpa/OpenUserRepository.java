package com.oauth.data.jpa;

import com.oauth.data.entity.OpenUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * Created by yjdini on 2017/8/15.
 *
 */
public interface OpenUserRepository extends JpaRepository<OpenUser, Integer>, QueryByExampleExecutor<OpenUser> {
    List<OpenUser> findByClientIdAndUserId(String clientId, Integer userId);
}
