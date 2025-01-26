package com.example.recommendation_system.web;

import com.example.recommendation_system.entities.Course;
import com.example.recommendation_system.entities.UserPreferences;
import com.example.recommendation_system.services.CourseRecommendationService;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.prefs.Preferences;

@RestController
@RequestMapping("/testapi")
public class TestingController {

    @Autowired
    private CourseRecommendationService courseRecommendationService;


    @PostMapping("/rec/{userId}")
    public ResponseEntity<?> recommendations(@RequestBody UserPreferences preferences, @PathVariable Long userId) {
        System.out.println(preferences.toString() +" PREF CONTROLLER");
        String response= courseRecommendationService.getCourseRecommendations(preferences,userId);
        System.out.println(response.toString() +"   responseresponseresponseresponseresponse CONTROLLER");
        return ResponseEntity.ok(response);
    }






}
