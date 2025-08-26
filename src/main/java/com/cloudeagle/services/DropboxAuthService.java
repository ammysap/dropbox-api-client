package com.cloudeagle.services;

import com.cloudeagle.config.DropBoxConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class DropboxAuthService {
    private static final String AUTH_URL = "https://www.dropbox.com/oauth2/authorize";
    private static final String TOKEN_URL = "https://api.dropbox.com/oauth2/token";

    private final DropBoxConfig config;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public DropboxAuthService(DropBoxConfig config) {
        this.config = config;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public String buildAuthorizationUrl() {
        return UriComponentsBuilder.fromHttpUrl(AUTH_URL)
                .queryParam("client_id", config.getClientId())
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", config.getRedirectUri())
                .queryParam("token_access_type", "offline")
                .toUriString();
    }

    public String exchangeCodeForToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(config.getClientId(), config.getClientSecret());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "code=" + code +
                "&grant_type=authorization_code" +
                "&redirect_uri=" + config.getRedirectUri();

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                TOKEN_URL, HttpMethod.POST, request, String.class);

        return parseAccessToken(response.getBody());
    }

    private String parseAccessToken(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            return root.has("access_token") ? root.get("access_token").asText() : null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse access token", e);
        }
    }
}
