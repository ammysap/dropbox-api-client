package com.cloudeagle.app;

import com.cloudeagle.config.DropBoxConfig;
import com.cloudeagle.services.DropboxAuthService;
import com.cloudeagle.services.DropboxApiService;

import java.util.Scanner;

public class DropboxApp {
    public static void main(String[] args) {
        DropBoxConfig config = new DropBoxConfig(
                "vzkz0gl6iii4pad",
                "8empo1rw3lo9m1f",
                "http://localhost:8080/callback");

        DropboxAuthService authService = new DropboxAuthService(config);
        DropboxApiService apiService = new DropboxApiService();

        // Get Authorization URL
        System.out.println("Visit this URL and authorize:");
        System.out.println(authService.buildAuthorizationUrl());

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the authorization code: ");
            String authCode = scanner.nextLine();

            // Exchange code for access token
            String accessToken = authService.exchangeCodeForToken(authCode);
            System.out.println("Access Token: " + accessToken);

            // Call Dropbox API
            String response = apiService.listTeamMembers(accessToken);
            System.out.println("API Response:");
            System.out.println(response);
        }
    }
}
