package com.example.recommendation_system.web.admin;

import com.example.recommendation_system.entities.User;
import com.example.recommendation_system.services.admin.AdminService;
import com.example.recommendation_system.services.jwt.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = adminService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/users/search/username/{username}")
    public ResponseEntity<List<User>> searchUsersByUsername(@PathVariable String username) {
        try {
            List<User> users = adminService.searchUsersByUsername(username);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/users/search/enrolled-course/{courseTitle}")
    public ResponseEntity<List<User>> searchUsersByEnrolledCourse(@PathVariable String courseTitle) {
        try {
            List<User> users = adminService.searchUsersByEnrolledCourseTitle(courseTitle);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
