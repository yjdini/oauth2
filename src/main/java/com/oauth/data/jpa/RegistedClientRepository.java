package com.oauth.data.jpa;

import com.oauth.data.entity.RegistedClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * Created by yjdini on 2017/8/15.
 *
 */
public interface RegistedClientRepository extends JpaRepository<RegistedClient, String>, QueryByExampleExecutor<RegistedClient> {
}
