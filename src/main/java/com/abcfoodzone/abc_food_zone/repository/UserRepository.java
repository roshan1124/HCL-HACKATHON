package com.abcfoodzone.abc_food_zone.repository;

import com.abcfoodzone.abc_food_zone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email (used for login)
    Optional<User> findByEmail(String email);

    // Check if email already exists (used for registration)
    boolean existsByEmail(String email);
}