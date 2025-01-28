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
import java.util.concurrent.ThreadLocalRandom;
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

            //List<String> userSkills = preferences.get().getSkills();

            // Fetch the user's skills
            List<String> userSkills = preferences
                    .map(UserPreferences::getSkills)
                    .orElseThrow(() -> new RuntimeException("User preferences not found"));

            // Randomize the skills order
            shuffleAndSelectSkills(userSkills);

            // If more than 4 skills, randomly select 4
            if (userSkills.size() > 4) {
                userSkills = userSkills.subList(0, 4);
            }

            // Prepare the request payload for Flask
            Map<String, Object> payload = new HashMap<>();
            payload.put("skills", userSkills);
           // payload.put("excludedCourses", excludedCourses);
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

    public static void shuffleAndSelectSkills(List<String> userSkills) {
        if (userSkills == null || userSkills.isEmpty()) {
            throw new RuntimeException("User skills not found");
        }

        // Custom shuffle using ThreadLocalRandom
        for (int i = userSkills.size() - 1; i > 0; i--) {
            int randomIndex = ThreadLocalRandom.current().nextInt(i + 1);
            // Swap elements
            String temp = userSkills.get(i);
            userSkills.set(i, userSkills.get(randomIndex));
            userSkills.set(randomIndex, temp);
        }

        // If more than 4 skills, randomly select 4
        if (userSkills.size() > 4) {
            userSkills = userSkills.subList(0, 4);
        }
    }

}
