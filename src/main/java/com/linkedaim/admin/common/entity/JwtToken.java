package com.linkedaim.admin.common.entity;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * JwtToken
 * @author zhaoyangyang
 * @date 2019-07-18
 */
public class JwtToken implements AuthenticationToken {
    /**
     * Token
     */
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
