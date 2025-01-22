package com.example.recommendation_system.services;

import com.example.recommendation_system.entities.Course;
import com.example.recommendation_system.entities.EnrolledCourse;
import com.example.recommendation_system.entities.User;
import com.example.recommendation_system.entities.UserPreferences;
import com.example.recommendation_system.repositories.CourseRepository;
import com.example.recommendation_system.repositories.EnrolledCourseRepository;
import com.example.recommendation_system.repositories.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseRecommendationService {
    private final RestTemplate restTemplate;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrolledCourseRepository enrolledCourseRepository;
    String flaskUrl = "http://localhost:5001/recommend";

    public CourseRecommendationService(RestTemplate restTemplate, CourseRepository courseRepository, UserRepository userRepository, EnrolledCourseRepository enrolledCourseRepository) {
        this.restTemplate = restTemplate;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.enrolledCourseRepository = enrolledCourseRepository;
    }

    public String getCourseRecommendations(UserPreferences preferences) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        Map<String,String> pref = new HashMap<>();
        pref.put("level",preferences.getLevel());
        pref.put("skills", String.valueOf(preferences.getSkills()));

        ResponseEntity<String> response = restTemplate.postForEntity(flaskUrl, pref, String.class);
        System.out.println(response.getBody() + " responseresponseresponseresponse");

        // Clear existing courses in the database
        courseRepository.deleteAll();

        // Convert Flask response to Course entities and save to database
        List<Course> newCourses = parseRecommendations(response.getBody());
        courseRepository.saveAll(newCourses);

        return response.getBody();
    }

    private List<Course> parseRecommendations(String flaskResponse) {
        List<Course> courses = new ArrayList<>();
        try {
            // Parse the JSON response into a list of maps
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> recommendations = objectMapper.readValue(flaskResponse, new TypeReference<>() {});

            // Convert each map into a Course entity
            for (Map<String, Object> recommendation : recommendations) {
                String title = (String) recommendation.get("Title");
                String category = ""; // Add a default or derived value if needed
                String level = (String) recommendation.get("Level");
                double numericRatings = Double.parseDouble(recommendation.get("Numeric Ratings").toString());
                int reviewCounts = (int) Double.parseDouble(recommendation.get("Review counts").toString());
                double similarity = Double.parseDouble(recommendation.get("Final_Score").toString());

                Course course = new Course();
                course.setTitle(title);
                course.setCategory(category);
                course.setLevel(level);
                course.setNumericRatings(numericRatings);
                course.setReviewCounts(reviewCounts);
                course.setSimilarity(similarity);

                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error parsing recommendations: " + e.getMessage());
        }
        return courses;
    }


}
