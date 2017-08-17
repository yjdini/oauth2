package com.ini.data.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yjdini on 2017/8/15.
 *
 */

@Entity
@Table(name = "UserLoginCode")
public class UserLoginCode {
    @Id
    private String loginCode;

    private Integer userId;

    private Date expires;

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }
}
