package com.example.recommendation_system.services.enrolledCourse;

import com.example.recommendation_system.entities.CompletedCourse;
import com.example.recommendation_system.entities.Course;
import com.example.recommendation_system.entities.EnrolledCourse;
import com.example.recommendation_system.entities.User;
import com.example.recommendation_system.repositories.CompletedCourseRepository;
import com.example.recommendation_system.repositories.CourseRepository;
import com.example.recommendation_system.repositories.EnrolledCourseRepository;
import com.example.recommendation_system.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrolledCourseServiceImpl implements EnrolledCourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrolledCourseRepository enrolledCourseRepository;
    private final CompletedCourseRepository completedCourseRepository;

    public EnrolledCourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository, EnrolledCourseRepository enrolledCourseRepository, CompletedCourseRepository completedCourseRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.enrolledCourseRepository = enrolledCourseRepository;
        this.completedCourseRepository = completedCourseRepository;
    }

    @Override
    public EnrolledCourse enrollInCourse(Long courseId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));

        Course course = courseRepository.findById(Math.toIntExact(courseId))
                .orElseThrow(() -> new IllegalArgumentException("Course with ID " + courseId + " not found"));

        EnrolledCourse enrolledCourse = new EnrolledCourse();
        enrolledCourse.setTitle(course.getTitle());
        enrolledCourse.setCategory(course.getCategory());
        enrolledCourse.setLevel(course.getLevel());
        enrolledCourse.setNumericRatings(course.getNumericRatings());
        enrolledCourse.setReviewCounts(course.getReviewCounts());
        enrolledCourse.setSimilarity(course.getSimilarity());
        enrolledCourse.setUser(user);

        return enrolledCourseRepository.save(enrolledCourse);
    }

    @Override
    public List<EnrolledCourse> getEnrolledCoursesByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));
        return enrolledCourseRepository.findEnrolledCourseByUser(user);
    }

    @Override
    public EnrolledCourse getEnrolledCourseById(Long id) {
        return enrolledCourseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Enrolled course with ID " + id + " not found"));
    }

    @Override
    public void deleteEnrolledCourse(Long id) {
        if (!enrolledCourseRepository.existsById(id)) {
            throw new IllegalArgumentException("Enrolled course with ID " + id + " does not exist");
        }
        enrolledCourseRepository.deleteById(id);
    }
    @Override
    public List<EnrolledCourse> searchEnrolledCoursesByTitle(String title) {
        return enrolledCourseRepository.findEnrolledCourseByTitleContainingIgnoreCase(title);
    }

    @Override
    public void updateCourseProgress(Long enrolledCourseId, int progress) {
        EnrolledCourse enrolledCourse = enrolledCourseRepository.findById(enrolledCourseId)
                .orElseThrow(() -> new IllegalArgumentException("Enrolled course with ID " + enrolledCourseId + " not found"));

        enrolledCourse.setProgress(progress);
        if (progress == 100) {
            CompletedCourse completedCourse = new CompletedCourse(
                    enrolledCourse.getTitle(),
                    enrolledCourse.getCategory(),
                    enrolledCourse.getLevel(),
                    enrolledCourse.getUser()
            );
            completedCourseRepository.save(completedCourse);
            enrolledCourseRepository.delete(enrolledCourse); // Optionally remove the enrolled course
        } else {
            enrolledCourseRepository.save(enrolledCourse);
        }
    }
    @Override
    public List<CompletedCourse> getCompletedCoursesByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));
        return completedCourseRepository.findCompletedCoursesByUser(user);
    }


}

