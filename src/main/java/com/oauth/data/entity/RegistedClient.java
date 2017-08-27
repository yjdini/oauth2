package com.oauth.data.entity;

import javax.persistence.*;

/**
 * Created by yjdini on 2017/8/15.
 *
 */

@Entity
@Table(name = "RegistedClient")
public class RegistedClient {
    @Id
    private String clientId;

    private String clientSecret;

    private String allowedHosts;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAllowedHosts() {
        return allowedHosts;
    }

    public void setAllowedHosts(String allowedHosts) {
        this.allowedHosts = allowedHosts;
    }
}
