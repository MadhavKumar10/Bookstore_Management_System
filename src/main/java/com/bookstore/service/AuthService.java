package com.bookstore.service;

import com.bookstore.dto.AuthRequest;
import com.bookstore.dto.AuthResponse;
import com.bookstore.entity.User;
import com.bookstore.repository.UserRepository;
import com.bookstore.util.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
/* import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.  8UsernamePasswordAuthenticationToken; */
import org.springframework.security.core.Authentication; 
/* import org.springframework.security.core.context.SecurityContextHolder; */
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService  {
    private final AuthenticationManager authenticationManager; 
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public String registerUser(AuthRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Ensure the role is set correctly
        try {
            user.setRole(User.Role.valueOf(request.getRole().toUpperCase())); 
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role. Allowed values: 'admin' or 'user'");
        }

        userRepository.save(user);
        return "User registered successfully";
    }

    public AuthResponse authenticateUser(AuthRequest request) {
        System.out.println("Authenticating user: " + request.getEmail());

        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            System.out.println("Authentication success for: " + request.getEmail());
    
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Authentication successful");
    
            Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
            if (userOptional.isEmpty()) {
                throw new RuntimeException("User not found");
            }
    
            User user = userOptional.get();
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

    
            System.out.println("Generated Token: " + token); // Debugging
    
            return new AuthResponse(token, user.getRole());
    
        } catch (Exception e) {
            System.err.println("Authentication failed: " + e.getMessage());
            throw new RuntimeException("Invalid email or password");
        }
    }   
}
