package com.cloudeagle.config;

public class DropBoxConfig {
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    public DropBoxConfig(String clientId, String clientSecret, String redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}
