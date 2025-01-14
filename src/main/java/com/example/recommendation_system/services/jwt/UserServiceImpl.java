package com.example.recommendation_system.services.jwt;

import com.example.recommendation_system.dto.SignupRequest;
import com.example.recommendation_system.dto.UserDto;
import com.example.recommendation_system.entities.User;
import com.example.recommendation_system.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
//@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsService UserDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findFirstByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(User::getUserDto)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

    @Override
    public UserDto updateUser(Long userId, SignupRequest signupRequest) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (signupRequest.getUsername() != null) {
                user.setUsername(signupRequest.getUsername());
            }
            if (signupRequest.getEmail() != null) {
                user.setEmail(signupRequest.getEmail());
            }
            if (signupRequest.getPassword() != null) {
                user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
            }
            return userRepository.save(user).getUserDto();
        }
        return null;
    }
}
