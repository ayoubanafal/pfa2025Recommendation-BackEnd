package com.example.recommendation_system.services.auth;

import com.example.recommendation_system.dto.SignupRequest;
import com.example.recommendation_system.dto.UserDto;
import com.example.recommendation_system.entities.User;
import com.example.recommendation_system.enums.UserRole;
import com.example.recommendation_system.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void createAnAdminAccount(){
        Optional<User> optionalUser = userRepository.findByUserRole(UserRole.Admin);
        if (optionalUser.isEmpty())
        {
            User user= new User();
            user.setEmail("admin@test.com");
            user.setUsername("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUserRole(UserRole.Admin);
            userRepository.save(user);
            System.out.println("Admin Account Created Successfully !");
        }
        else {
            System.out.println("Admin Account Already exists !!");
        }


        Optional<User> optionalUser1 = userRepository.findByUserRole(UserRole.User);
        if (optionalUser1.isEmpty())
        {
            User user= new User();
            user.setEmail("user@test.com");
            user.setUsername("user");
            user.setPassword(new BCryptPasswordEncoder().encode("user"));
            user.setUserRole(UserRole.User);
            userRepository.save(user);
            System.out.println("user Account Created Successfully !");
        }
        else {
            System.out.println("user Account Already exists !!");
        }
    }

    @Override
    public UserDto signupUser(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setUsername(signupRequest.getUsername());
        System.out.println(signupRequest.getPassword());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        System.out.println(user.getPassword());
        user.setUserRole(UserRole.User);
        User createdUser=userRepository.save(user);
        return createdUser.getUserDto();
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}