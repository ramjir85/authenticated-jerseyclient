package com.au.tupolevbase.authenticatedrestclient.domain;

public class TokenRequest {

    private String appId;
    private String secretKey;

    public TokenRequest() {
    }

    public TokenRequest(String appId, String secretKey) {
        this.appId = appId;
        this.secretKey = secretKey;
    }

    public String getAppId() {
        return appId;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
