package com.au.tupolevbase.authenticatedrestclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "restclient")
public class AuthenticatedRestClientConfiguration {


    @Value("authUrl")
    private String authUrl;

    @Value("appId")
    private String appId;

    @Value("secret")
    private String secret;


    @Value("tlsVersion")
    String tlsVersion;

    public AuthenticatedRestClientConfiguration() {
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getTlsVersion() {
        return tlsVersion;
    }

    public void setTlsVersion(String tlsVersion) {
        this.tlsVersion = tlsVersion;
    }
}
