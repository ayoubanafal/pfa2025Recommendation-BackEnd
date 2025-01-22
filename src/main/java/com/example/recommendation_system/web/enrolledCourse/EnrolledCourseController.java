package com.example.recommendation_system.web.enrolledCourse;

import com.example.recommendation_system.entities.EnrolledCourse;
import com.example.recommendation_system.services.enrolledCourse.EnrolledCourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrolled-courses")
public class EnrolledCourseController {
    private final EnrolledCourseService enrolledCourseService;
////////////// i need progress in the enrolled courses
    
    public EnrolledCourseController(EnrolledCourseService enrolledCourseService) {
        this.enrolledCourseService = enrolledCourseService;
    }

    @PostMapping("/enroll")
    public ResponseEntity<EnrolledCourse> enrollInCourse(@RequestParam Long courseId, @RequestParam Long userId) {
        EnrolledCourse enrolledCourse = enrolledCourseService.enrollInCourse(courseId, userId);
        return ResponseEntity.ok(enrolledCourse);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EnrolledCourse>> getEnrolledCoursesByUser(@PathVariable Long userId) {
        List<EnrolledCourse> enrolledCourses = enrolledCourseService.getEnrolledCoursesByUser(userId);
        return ResponseEntity.ok(enrolledCourses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrolledCourse> getEnrolledCourseById(@PathVariable Long id) {
        EnrolledCourse enrolledCourse = enrolledCourseService.getEnrolledCourseById(id);
        return ResponseEntity.ok(enrolledCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrolledCourse(@PathVariable Long id) {
        enrolledCourseService.deleteEnrolledCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<EnrolledCourse>> searchEnrolledCourses(@RequestParam String title) {
        List<EnrolledCourse> results = enrolledCourseService.searchEnrolledCoursesByTitle(title);
        return ResponseEntity.ok(results);
    }
}
