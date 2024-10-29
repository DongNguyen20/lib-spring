package com.kopw.spring.controller;

import com.kopw.spring.model.AuthRequest;
import com.kopw.spring.model.dto.AuthResponse;
import com.kopw.spring.model.dto.TokenRefreshRequest;
import com.kopw.spring.model.dto.UserRegister;
import com.kopw.spring.model.entity.UserEntity;
import com.kopw.spring.repository.UserRepository;
import com.kopw.spring.service.JwtService;
import com.kopw.spring.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest loginRequest) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenService.generateAccessToken((UserDetails) authentication.getPrincipal());
        String refreshToken = tokenService.generateRefreshToken((UserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(AuthResponse.builder().token(jwt).refreshToken(refreshToken).build());
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

    @PostMapping("/refresh-access-token")
    public ResponseEntity<AuthResponse> refreshAccessToken(@RequestBody TokenRefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        try {
            String username = jwtService.extractUsername(refreshToken);
            UserDetails userDetails = userRepository.findByUsername(username).
                    orElseThrow(() -> new UsernameNotFoundException(username));

            if (Boolean.TRUE.equals(jwtService.validateToken(refreshToken, userDetails))) {
                String newAccessToken = tokenService.generateAccessToken(userDetails);
                String newRefreshToken = tokenService.generateRefreshToken(userDetails);
                return ResponseEntity.ok(new AuthResponse(newAccessToken, newRefreshToken));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
