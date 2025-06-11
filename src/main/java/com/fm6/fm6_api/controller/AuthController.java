package com.fm6.fm6_api.controller;

import com.fm6.fm6_api.dto.AuthRequest;
import com.fm6.fm6_api.dto.AuthResponse;
import com.fm6.fm6_api.entity.User;
import com.fm6.repository.UserRepository;
import com.fm6.security.JwtService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepo;
    private final AuthenticationManager authManager;
    private final JwtService jwt;

    public AuthController(UserRepository userRepo,
                          AuthenticationManager authManager,
                          JwtService jwt) {
        this.userRepo = userRepo;
        this.authManager = authManager;
        this.jwt = jwt;
    }

    /** POST /auth/register */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest req) {
        if (userRepo.findByUsername(req.getUsername()).isPresent())
            return ResponseEntity.badRequest().body(new AuthResponse("username already exists"));
        User u = new User();
        u.setUsername(req.getUsername());
        u.setPasswordHash(req.getPassword());   // hashé dans setter
        u.setRole("PARTENAIRE");
        userRepo.save(u);
        String token = jwt.generateToken(u.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    /** POST /auth/login */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        String token = jwt.generateToken(req.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
