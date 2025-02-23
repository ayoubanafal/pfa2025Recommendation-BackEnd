package com.example.recommendation_system.web.enrolledCourse;

import com.example.recommendation_system.entities.CompletedCourse;
import com.example.recommendation_system.entities.EnrolledCourse;
import com.example.recommendation_system.repositories.CourseRepository;
import com.example.recommendation_system.services.enrolledCourse.EnrolledCourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/enrolled-courses")
public class EnrolledCourseController {
    private final EnrolledCourseService enrolledCourseService;
    private final CourseRepository courseRepository;
////////////// i need progress in the enrolled courses
    
    public EnrolledCourseController(EnrolledCourseService enrolledCourseService, CourseRepository courseRepository) {
        this.enrolledCourseService = enrolledCourseService;
        this.courseRepository = courseRepository;
    }

    @PostMapping("/enroll")
    public ResponseEntity<EnrolledCourse> enrollInCourse(@RequestParam String title, @RequestParam Long userId) {//@RequestParam Long courseId
        System.out.println(title);
        System.out.println(courseRepository.findCourseByTitle(title));
        Long courseId = courseRepository.findCourseByTitle(title).getId();
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

    // when doing this one from the frontend give the user the choice to go up by 10% each time
    @PatchMapping("/{id}/progress")
    public ResponseEntity<Void> updateProgress(@PathVariable Long id, @RequestParam int progress) {
        enrolledCourseService.updateCourseProgress(id, progress);
        return ResponseEntity.noContent().build();
    }

    // completed courses
    @GetMapping("/user/completed/{userId}")
    public ResponseEntity<List<CompletedCourse>> getCompletedCoursesByUser(@PathVariable Long userId) {
        List<CompletedCourse> completedCourses = enrolledCourseService.getCompletedCoursesByUser(userId);
        return ResponseEntity.ok(completedCourses);
    }

    // Unenroll from a course
    @DeleteMapping("/{courseId}/unenroll/{userId}")
    public ResponseEntity<String> unenrollFromCourse(@PathVariable Long courseId, @PathVariable Long userId) {
        enrolledCourseService.unenrollFromCourse(courseId, userId);
        return ResponseEntity.ok("Successfully unenrolled from the course.");
    }


}
