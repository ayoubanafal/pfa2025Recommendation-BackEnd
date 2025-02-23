package com.example.recommendation_system.services.userPreferences;

import com.example.recommendation_system.entities.User;
import com.example.recommendation_system.entities.UserPreferences;

import java.util.List;
import java.util.Optional;

public interface UserPreferencesService {

    UserPreferences createUserPreferences(User user,UserPreferences userPreferences);

    Optional<UserPreferences> getUserPreferencesByUserId(Long userId);

    List<UserPreferences> getAllUserPreferences();

//    UserPreferences updateUserPreferences(Long id, UserPreferences userPreferences);

    void deleteUserPreferences(Long userId,List<String> skills);
}
