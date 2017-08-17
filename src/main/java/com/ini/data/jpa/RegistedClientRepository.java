package com.ini.data.jpa;

import com.ini.data.entity.OpenUser;
import com.ini.data.entity.RegistedClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * Created by yjdini on 2017/8/15.
 *
 */
public interface RegistedClientRepository extends JpaRepository<RegistedClient, String>, QueryByExampleExecutor<RegistedClient> {
}
