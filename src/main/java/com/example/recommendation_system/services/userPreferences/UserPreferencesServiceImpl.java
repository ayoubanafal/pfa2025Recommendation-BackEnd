package com.example.recommendation_system.services.userPreferences;

import com.example.recommendation_system.entities.User;
import com.example.recommendation_system.entities.UserPreferences;
import com.example.recommendation_system.repositories.UserPreferencesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserPreferencesServiceImpl implements UserPreferencesService {
    private final UserPreferencesRepository userPreferencesRepository;

    public UserPreferencesServiceImpl(UserPreferencesRepository userPreferencesRepository) {
        this.userPreferencesRepository = userPreferencesRepository;
    }

    @Override
    public UserPreferences createUserPreferences(User user, UserPreferences preferences) {
        // Find existing preferences using userId
        UserPreferences existingPreferences = userPreferencesRepository
                .findByUserId(user.getId()) // Assuming you have a method to find preferences by userId
                .orElse(null); // No preferences for this user, return null if not found

        if (existingPreferences != null) {
            // Merge skills to avoid duplicates
            List<String> existingSkills = existingPreferences.getSkills();
            List<String> newSkills = preferences.getSkills();

            // Remove already existing skills
            newSkills.removeAll(existingSkills);

            // Add new skills to the existing skills
            existingSkills.addAll(newSkills);
            existingPreferences.setSkills(existingSkills);
            existingPreferences.setLevel(preferences.getLevel());
            existingPreferences.setPreferredCategory(preferences.getPreferredCategory());

            return userPreferencesRepository.save(existingPreferences);
        }

        // If no existing preferences, set the userId and save the new preferences
        preferences.setUserId(user.getId());
        return userPreferencesRepository.save(preferences);
    }


    @Override
    public Optional<UserPreferences> getUserPreferencesByUserId(Long userId) {
        return userPreferencesRepository.findByUserId(userId);
    }

    @Override
    public List<UserPreferences> getAllUserPreferences() {
        return userPreferencesRepository.findAll();
    }

//    @Override
//    public UserPreferences updateUserPreferences(Long id, UserPreferences userPreferences) {
//        UserPreferences existingPreferences = userPreferencesRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("UserPreferences with ID " + id + " not found"));
//
//        // Ensure no duplicate skills are stored
//        List<String> combinedSkills = userPreferences.getSkills();
//        combinedSkills.addAll(existingPreferences.getSkills()); // Combine old and new skills
//        List<String> uniqueSkills = combinedSkills.stream().distinct().toList();
//
//        existingPreferences.setLevel(userPreferences.getLevel());
//        existingPreferences.setPreferredCategory(userPreferences.getPreferredCategory());
//        existingPreferences.setUserId(userPreferences.getUserId());
//        existingPreferences.setSkills(uniqueSkills); // Set the unique skills list
//
//        return userPreferencesRepository.save(existingPreferences);
//    }

    @Override
    public void deleteUserPreferences(Long userId, List<String> skillsToRemove) {
        // Find existing preferences for the user
        UserPreferences existingPreferences = userPreferencesRepository
                .findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("UserPreferences for userId " + userId + " not found"));

        // Remove the specified skills from the user's preferences
        List<String> currentSkills = existingPreferences.getSkills();
        currentSkills.removeAll(skillsToRemove);
        existingPreferences.setSkills(currentSkills);

        // Save the updated preferences back to the repository
        userPreferencesRepository.save(existingPreferences);
    }

}