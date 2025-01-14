package com.example.recommendation_system.web;

import com.example.recommendation_system.entities.Course;
import com.example.recommendation_system.entities.UserPreferences;
import com.example.recommendation_system.services.CourseRecommendationService;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.prefs.Preferences;

@RestController
@RequestMapping("/testapi")
public class TestingController {

    @Autowired
    private CourseRecommendationService courseRecommendationService;


    @PostMapping("/rec")
    public ResponseEntity<?> recommendations(@RequestBody UserPreferences preferences) {
        System.out.println(preferences.toString() +" PREF CONTROLLER");
        String response= courseRecommendationService.getCourseRecommendations(preferences);
        System.out.println(response.toString() +"   responseresponseresponseresponseresponse CONTROLLER");
        return ResponseEntity.ok(response);
    }






}
