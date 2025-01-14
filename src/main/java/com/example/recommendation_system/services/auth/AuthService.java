package com.example.recommendation_system.services.auth;

import com.example.recommendation_system.dto.SignupRequest;
import com.example.recommendation_system.dto.UserDto;

public interface AuthService {
    UserDto signupUser(SignupRequest signupRequest);
    boolean hasUserWithEmail(String email);
}
