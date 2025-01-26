package com.example.recommendation_system.repositories;

import com.example.recommendation_system.entities.Course;
import com.example.recommendation_system.entities.EnrolledCourse;
import com.example.recommendation_system.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EnrolledCourseRepository extends JpaRepository<EnrolledCourse, Long> {
    List<EnrolledCourse> findEnrolledCourseByUser(User user);
    List<EnrolledCourse> findEnrolledCourseByTitleContainingIgnoreCase(String title);
    EnrolledCourse findEnrolledCourseByTitleContaining(String title);
}
