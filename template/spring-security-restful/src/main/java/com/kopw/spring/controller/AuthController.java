package com.kopw.spring.controller;

import com.kopw.spring.model.AuthRequest;
import com.kopw.spring.model.dto.AuthResponse;
import com.kopw.spring.model.dto.UserRegister;
import com.kopw.spring.model.entity.UserEntity;
import com.kopw.spring.repository.UserRepository;
import com.kopw.spring.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest loginRequest) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateToken((UserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(AuthResponse.builder().token(jwt).build());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegister userRegister) {
        if (userRepository.existsByUsername(userRegister.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        UserEntity user = UserEntity.builder().username(userRegister.getUsername())
                .password(passwordEncoder.encode(userRegister.getPassword()))
                .role(userRegister.getRole()).build();
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyToken(@RequestParam("token") String token) {
        var isValid = jwtService.isTokenValid(token);
        return Boolean.TRUE.equals(isValid) ? ResponseEntity.ok("Token is valid.") :
                ResponseEntity.status(401).body("Token is invalid.");
    }
}
