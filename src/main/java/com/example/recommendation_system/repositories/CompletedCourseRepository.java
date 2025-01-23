package com.example.recommendation_system.repositories;

import com.example.recommendation_system.entities.CompletedCourse;
import com.example.recommendation_system.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompletedCourseRepository extends JpaRepository<CompletedCourse, Long> {
    List<CompletedCourse> findCompletedCoursesByUser(User user);
}
