package com.klu.library.controller;

import com.klu.library.dto.LoginRequest;
import com.klu.library.dto.LoginResponse;
import com.klu.library.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    // Demo users for this lab. Replace with database-backed users in a real application.
    private final Map<String, DemoUser> users;

    public AuthController(JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.users = Map.of(
            "admin", new DemoUser(passwordEncoder.encode("admin123"), "ADMIN"),
            "librarian", new DemoUser(passwordEncoder.encode("lib123"), "LIBRARIAN"),
            "user", new DemoUser(passwordEncoder.encode("user123"), "USER")
        );
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        DemoUser user = users.get(request.getUsername());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.passwordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        String token = jwtService.generateToken(request.getUsername(), user.role());
        return new LoginResponse(token, request.getUsername(), user.role());
    }

    private record DemoUser(String passwordHash, String role) {}
}
