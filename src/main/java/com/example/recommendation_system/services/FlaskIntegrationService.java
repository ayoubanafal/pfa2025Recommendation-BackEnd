package com.example.recommendation_system.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class FlaskIntegrationService {

    String flaskUrl = "http://localhost:5001/recommend";

    private final RestTemplate restTemplate;

    public FlaskIntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Object getCourseRecommendations(Map<String, String> userPreferences) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(userPreferences, headers);

            ResponseEntity<Object> response = restTemplate.postForEntity(flaskUrl, entity, Object.class);

            return response.getBody();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
