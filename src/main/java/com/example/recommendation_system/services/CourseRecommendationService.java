package com.example.recommendation_system.services;

import com.example.recommendation_system.entities.UserPreferences;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CourseRecommendationService {
    private final RestTemplate restTemplate;
    String flaskUrl = "http://localhost:5001/recommend";

    public CourseRecommendationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getCourseRecommendations(UserPreferences preferences) {
        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", "application/json");

        Map<String,String> pref = new HashMap<>();

        pref.put("level",preferences.getLevel());
        pref.put("skills", String.valueOf(preferences.getSkills()));

        ResponseEntity<String> response = restTemplate.postForEntity(flaskUrl, pref, String.class);

        System.out.println(response.getBody() + " responseresponseresponseresponse");

        return response.getBody();
    }
}
