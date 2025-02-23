package com.example.recommendation_system.web.userPreferences;

import com.example.recommendation_system.entities.User;
import com.example.recommendation_system.entities.UserPreferences;
import com.example.recommendation_system.repositories.UserRepository;
import com.example.recommendation_system.services.userPreferences.UserPreferencesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-preferences")
@CrossOrigin(origins = "http://localhost:4200")
public class UserPreferencesController {

    private final UserPreferencesService userPreferencesService;
    private final UserRepository userRepository;

    public UserPreferencesController(UserPreferencesService userPreferencesService, UserRepository userRepository) {
        this.userPreferencesService = userPreferencesService;
        this.userRepository = userRepository;
    }

    // Create UserPreferences
    @PostMapping
    public ResponseEntity<UserPreferences> createUserPreferences(@RequestBody UserPreferences userPreferences) {
        User user= userRepository.findUserById(userPreferences.getUserId());
        return ResponseEntity.ok(userPreferencesService.createUserPreferences(user,userPreferences));
    }


    // Get UserPreferences by User ID////////////////////////////
    @GetMapping("/user/{userId}")
    public ResponseEntity<Optional<UserPreferences>> getUserPreferencesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userPreferencesService.getUserPreferencesByUserId(userId));
    }


    // Get all UserPreferences
    @GetMapping
    public ResponseEntity<List<UserPreferences>> getAllUserPreferences() {
        return ResponseEntity.ok(userPreferencesService.getAllUserPreferences());
    }

    // Update UserPreferences
//    @PutMapping("/{id}")
//    public ResponseEntity<UserPreferences> updateUserPreferences(@PathVariable Long id, @RequestBody UserPreferences userPreferences,@PathVariable Long userId) {
//        User user= userRepository.findUserById(userId);
//        return ResponseEntity.ok(userPreferencesService.updateUserPreferences(id, userPreferences));
//    }

    // Delete UserPreferences
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserPreferences(@PathVariable Long userId,@RequestBody List<String> skills) {
        userPreferencesService.deleteUserPreferences(userId,skills);
        return ResponseEntity.ok("UserPreferences with ID " + userId + " has been deleted.");
    }
}
