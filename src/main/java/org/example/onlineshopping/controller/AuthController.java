package org.example.onlineshopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.dto.response.AuthResponse;
import org.example.onlineshopping.dto.request.LoginRequestDto;
import org.example.onlineshopping.dto.request.RegisterRequestDto;
import org.example.onlineshopping.entity.User;
import org.example.onlineshopping.enums.Role;
import org.example.onlineshopping.repository.UserRepository;
import org.example.onlineshopping.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDto registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody LoginRequestDto loginRequest) {
        System.out.println(loginRequest.getUsername());
        User user = userRepository.findByUsername(loginRequest.getUsername());

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtils.generateJwtToken(
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole().name())
                        .build());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
