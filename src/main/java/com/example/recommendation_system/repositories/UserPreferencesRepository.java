package com.example.recommendation_system.repositories;

import com.example.recommendation_system.entities.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Integer> {
    Optional<UserPreferences> findById(Long id);

    Optional<UserPreferences> findByLevelAndSkills(String level, String skills);
}
