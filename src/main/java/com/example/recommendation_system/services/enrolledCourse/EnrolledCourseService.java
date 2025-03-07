package com.example.recommendation_system.services.enrolledCourse;

import com.example.recommendation_system.entities.CompletedCourse;
import com.example.recommendation_system.entities.EnrolledCourse;

import java.util.List;

public interface EnrolledCourseService {
    EnrolledCourse enrollInCourse(Long courseId, Long userId);
    List<EnrolledCourse> getEnrolledCoursesByUser(Long userId);
    EnrolledCourse getEnrolledCourseById(Long id);
    void deleteEnrolledCourse(Long id);
    List<EnrolledCourse> searchEnrolledCoursesByTitle(String title);
    void updateCourseProgress(Long enrolledCourseId, int progress);
    List<CompletedCourse> getCompletedCoursesByUser(Long userId);
    void unenrollFromCourse(Long courseId, Long userId);
}
