package com.ini.data.jpa;

import com.ini.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * Created by yjdini on 2017/8/15.
 *
 */
public interface UserRepository extends JpaRepository<User, Integer>, QueryByExampleExecutor<User> {
}
