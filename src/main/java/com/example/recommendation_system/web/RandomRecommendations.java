package com.example.recommendation_system.web;

import com.example.recommendation_system.entities.CompletedCourse;
import com.example.recommendation_system.entities.EnrolledCourse;
import com.example.recommendation_system.entities.User;
import com.example.recommendation_system.entities.UserPreferences;
import com.example.recommendation_system.repositories.*;
import com.example.recommendation_system.services.CourseRecommendationService;
import com.example.recommendation_system.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.prefs.Preferences;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class RandomRecommendations {
    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private CourseRecommendationService courseRecommendationService;

    private final RestTemplate restTemplate;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrolledCourseRepository enrolledCourseRepository;
    private final CompletedCourseRepository completedCourseRepository;
    String flaskUrl = "http://localhost:5001/top_courses_by_skill_and_level";
    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    public RandomRecommendations(RestTemplate restTemplate, CourseRepository courseRepository, UserRepository userRepository, EnrolledCourseRepository enrolledCourseRepository, CompletedCourseRepository completedCourseRepository) {
        this.restTemplate = restTemplate;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.enrolledCourseRepository = enrolledCourseRepository;
        this.completedCourseRepository = completedCourseRepository;
    }


    @PostMapping("/top-courses/{userId}")
    public ResponseEntity<?> getTopCoursesBySkills(@PathVariable Long userId) {
        try {
            // Fetch the user by ID
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
            Optional<UserPreferences> preferences = userPreferencesRepository.findByUserId(userId);

            // Fetch enrolled and completed courses for the user
            List<String> enrolledCourses = enrolledCourseRepository
                    .findEnrolledCourseByUser(user)
                    .stream()
                    .map(EnrolledCourse::getTitle)
                    .toList();

            List<String> completedCourses = completedCourseRepository
                    .findCompletedCoursesByUser(user)
                    .stream()
                    .map(CompletedCourse::getTitle)
                    .toList();

            // Combine enrolled and completed courses into a single exclusion list
            List<String> excludedCourses = new ArrayList<>();
            excludedCourses.addAll(enrolledCourses);
            excludedCourses.addAll(completedCourses);

            // Fetch the user's skills (use a placeholder if needed)
            List<String> userSkills = preferences.get().getSkills(); // Replace this with user skills retrieval logic

            // Prepare the request payload for Flask
            Map<String, Object> payload = new HashMap<>();
            payload.put("skills", userSkills);  // Skills should be a list of strings
           // payload.put("excludedCourses", excludedCourses);// Exclude already enrolled/completed courses
            payload.put("enrolledCourses", enrolledCourses);
            payload.put("completedCourses", completedCourses);
            System.out.println("Payload being sent to Flask: " + payload);

            // Send the request to Flask and get the response
            ResponseEntity<String> response = restTemplate.postForEntity(flaskUrl, payload, String.class);

            // Return the response from Flask
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching top courses: " + e.getMessage());
        }
    }



}
