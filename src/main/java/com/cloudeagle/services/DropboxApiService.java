package com.cloudeagle.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class DropboxApiService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public DropboxApiService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public String listTeamMembers(String accessToken) {
        String apiUrl = "https://api.dropboxapi.com/2/team/members/list";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"limit\":10}";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    URI.create(apiUrl), request, String.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Error calling Dropbox API", e);
        }
    }
}
