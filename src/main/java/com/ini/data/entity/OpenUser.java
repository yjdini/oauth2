package com.ini.data.entity;

import javax.persistence.*;

/**
 * Created by yjdini on 2017/8/15.
 *
 */

@Entity
@Table(name = "OpenUser")
public class OpenUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer openId;

    private String clientId;

    private Integer userId;

    public Integer getOpenId() {
        return openId;
    }

    public void setOpenId(Integer openId) {
        this.openId = openId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
