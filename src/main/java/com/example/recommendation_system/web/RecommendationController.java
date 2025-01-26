package com.example.recommendation_system.web;


import com.example.recommendation_system.entities.Course;
import com.example.recommendation_system.entities.User;
import com.example.recommendation_system.entities.UserPreferences;
import com.example.recommendation_system.repositories.UserRepository;
import com.example.recommendation_system.services.CourseRecommendationService;
import com.example.recommendation_system.services.RecommendationService;
import com.example.recommendation_system.services.url.CourseraUrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private CourseRecommendationService courseRecommendationService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/preferences/{userId}")
    public ResponseEntity<?> savePreferences(@RequestBody UserPreferences preferences,@PathVariable Long userId) {
        try {
            User user = userRepository.findUserById(userId);
            UserPreferences savedPreferences = recommendationService.savePreferences(user,preferences);
            return ResponseEntity.ok(savedPreferences);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving preferences: " + e.getMessage());
        }
    }
    //////// after this is called hte courses pulled from the model are stored for enrolling use
    @PostMapping("/courses/{userId}")
    public ResponseEntity<?> getCourseRecommendations(@RequestBody UserPreferences preferences,@PathVariable Long userId ) {
        try {
            User user = userRepository.findUserById(userId);
            recommendationService.savePreferences(user,preferences);
            String recommendations = courseRecommendationService.getCourseRecommendations(preferences,userId);
            return ResponseEntity.ok(recommendations);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching recommendations: " + e.getMessage());
        }
    }
    ///// url

//    @GetMapping("/api/get-coursera-url")
//    public ResponseEntity<String> getCourseraUrl(@RequestParam("title") String courseTitle) {
//        try {
//            String courseraUrl = CourseraUrlGenerator.getCourseraUrl(courseTitle);
//            if (courseraUrl.equals("Course not found")) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(courseraUrl);
//            }
//            return ResponseEntity.ok(courseraUrl);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error generating Coursera URL: " + e.getMessage());
//        }
//    }


    @GetMapping("/get-coursera-url")
    public String getCourseraUrl(@RequestParam String courseTitle) {
        // Replace spaces with '%20'
        String urlFriendlyTitle = courseTitle.replaceAll(" ", "%20");

        // Construct the search URL for Coursera
        String courseraUrl = "https://www.coursera.org/search?query=" + urlFriendlyTitle;

        return courseraUrl;
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
