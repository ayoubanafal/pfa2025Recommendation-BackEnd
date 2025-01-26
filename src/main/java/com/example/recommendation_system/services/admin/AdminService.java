package com.example.recommendation_system.services.admin;

import com.example.recommendation_system.entities.EnrolledCourse;
import com.example.recommendation_system.entities.User;
import com.example.recommendation_system.repositories.EnrolledCourseRepository;
import com.example.recommendation_system.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private UserRepository userRepository;
    private EnrolledCourseRepository enrolledCourseRepository;

    public AdminService(UserRepository userRepository, EnrolledCourseRepository enrolledCourseRepository) {
        this.userRepository = userRepository;
        this.enrolledCourseRepository = enrolledCourseRepository;
    }

    // Get all users and their courses for admin
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Search users by username for admin
    public List<User> searchUsersByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    // Search users by enrolled course title for admin
    public List<User> searchUsersByEnrolledCourseTitle(String courseTitle) {
        EnrolledCourse enrolledCourse = enrolledCourseRepository.findEnrolledCourseByTitleContaining(courseTitle);
        return userRepository.findUserByEnrolledCourses(enrolledCourse);
    }
}
