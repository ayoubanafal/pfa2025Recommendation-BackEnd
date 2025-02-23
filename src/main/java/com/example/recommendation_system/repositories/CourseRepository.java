package com.example.recommendation_system.repositories;

import com.example.recommendation_system.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findAllByOrderBySimilarityDesc();
    List<Course> findByCategory(String category);
    Course findCourseByTitle(String title);

}
