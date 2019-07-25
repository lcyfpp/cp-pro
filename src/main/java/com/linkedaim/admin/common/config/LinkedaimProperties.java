package com.linkedaim.admin.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class LinkedaimProperties {

    // 是否显示sql日志
    @Value("${linkedaim.showsql}")
    private boolean showsql;
    // JWT认证加密私钥(Base64加密)
    @Value("${linkedaim.encryptJWTKey}")
    private String encryptJWTKey;
    // AccessToken过期时间-5分钟-5*60(秒为单位)
    @Value("${linkedaim.accessTokenExpireTime}")
    private String accessTokenExpireTime;
    // RefreshToken过期时间-30分钟-30*60(秒为单位)
    @Value("${linkedaim.refreshTokenExpireTime}")
    private String refreshTokenExpireTime;
    // Shiro缓存过期时间-5分钟-5*60(秒为单位)(一般设置与AccessToken过期时间一致)
    @Value("${linkedaim.shiroCacheExpireTime}")
    private String shiroCacheExpireTime;

    public boolean isShowsql() {
        return showsql;
    }

    public void setShowsql(boolean showsql) {
        this.showsql = showsql;
    }

    public String getEncryptJWTKey() {
        return encryptJWTKey;
    }

    public void setEncryptJWTKey(String encryptJWTKey) {
        this.encryptJWTKey = encryptJWTKey;
    }

    public String getAccessTokenExpireTime() {
        return accessTokenExpireTime;
    }

    public void setAccessTokenExpireTime(String accessTokenExpireTime) {
        this.accessTokenExpireTime = accessTokenExpireTime;
    }

    public String getRefreshTokenExpireTime() {
        return refreshTokenExpireTime;
    }

    public void setRefreshTokenExpireTime(String refreshTokenExpireTime) {
        this.refreshTokenExpireTime = refreshTokenExpireTime;
    }

    public String getShiroCacheExpireTime() {
        return shiroCacheExpireTime;
    }

    public void setShiroCacheExpireTime(String shiroCacheExpireTime) {
        this.shiroCacheExpireTime = shiroCacheExpireTime;
    }
}
