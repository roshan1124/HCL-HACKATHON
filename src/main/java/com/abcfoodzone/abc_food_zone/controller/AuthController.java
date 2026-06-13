package com.abcfoodzone.abc_food_zone.controller;

import com.abcfoodzone.abc_food_zone.dto.LoginRequest;
import com.abcfoodzone.abc_food_zone.dto.LoginResponse;
import com.abcfoodzone.abc_food_zone.dto.RegisterRequest;
import com.abcfoodzone.abc_food_zone.dto.RegisterResponse;
import com.abcfoodzone.abc_food_zone.entity.User;
import com.abcfoodzone.abc_food_zone.repository.UserRepository;
import com.abcfoodzone.abc_food_zone.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new RegisterResponse("Email already exists!", null, null, null, null));
        }

        // Convert role string to enum
        User.Role role;
        try {
            role = User.Role.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new RegisterResponse("Invalid role! Use ADMIN or CUSTOMER", null, null, null, null));
        }

        // Create and save user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        User savedUser = userRepository.save(user);

        // Return response
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterResponse("User registered successfully!", savedUser.getId(),
                        savedUser.getEmail(), savedUser.getName(), savedUser.getRole().toString()));
    }

    // Login endpoint - SIMPLE VERSION
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // Try to authenticate
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, "Invalid email or password!", null, null));
        }

        // If authenticated, generate token
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        // Get user role
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);
        String role = user != null ? user.getRole().toString() : "";

        // Return token
        return ResponseEntity.ok(new LoginResponse(token, "Login successful!", request.getEmail(), role));
    }
}