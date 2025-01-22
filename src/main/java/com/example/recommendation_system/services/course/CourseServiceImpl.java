package com.example.recommendation_system.services.course;

import com.example.recommendation_system.entities.Course;
import com.example.recommendation_system.repositories.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl {
    public CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    // Delete all courses before putting new ones in
    @Transactional
    public void deleteAllCourses() {
        courseRepository.deleteAll();
    }

    // Save the courses coming from the flusk
    @Transactional
    public void saveAllCourses(List<Course> courses) {
        courseRepository.saveAll(courses);
    }
}
