package com.example.recommendation_system.services.jwt;

import com.example.recommendation_system.dto.SignupRequest;
import com.example.recommendation_system.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    public UserDetailsService UserDetailsService();
    UserDto getUserById(Long Id);
    UserDto updateUser(Long userId, SignupRequest signupRequest);
}
