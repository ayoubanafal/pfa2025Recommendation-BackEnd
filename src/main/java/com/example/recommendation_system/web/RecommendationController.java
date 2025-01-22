package com.example.recommendation_system.web;

import com.example.recommendation_system.entities.Course;
import com.example.recommendation_system.entities.UserPreferences;
import com.example.recommendation_system.services.CourseRecommendationService;
import com.example.recommendation_system.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private CourseRecommendationService courseRecommendationService;

    @PostMapping("/preferences")
    public ResponseEntity<?> savePreferences(@RequestBody UserPreferences preferences) {
        try {
            UserPreferences savedPreferences = recommendationService.savePreferences(preferences);
            return ResponseEntity.ok(savedPreferences);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving preferences: " + e.getMessage());
        }
    }
//////// after this is called hte courses pulled from the model are stored for enrolling use
    @PostMapping("/courses")
    public ResponseEntity<?> getCourseRecommendations(@RequestBody UserPreferences preferences) {
        try {
            String recommendations = courseRecommendationService.getCourseRecommendations(preferences);
            return ResponseEntity.ok(recommendations);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching recommendations: " + e.getMessage());
        }
    }
//// not working
    @GetMapping("/courses")
    public ResponseEntity<?> getAllCourses() {
        try {
            return ResponseEntity.ok(recommendationService.getAllCourses());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching courses: " + e.getMessage());
        }
    }
}
