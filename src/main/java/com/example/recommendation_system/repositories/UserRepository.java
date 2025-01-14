package com.example.recommendation_system.repositories;

import com.example.recommendation_system.entities.User;
import com.example.recommendation_system.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findFirstByEmail(String email);
    Optional<User> findByUserRole(UserRole userRole);
    User findByEmail(String email);

}
