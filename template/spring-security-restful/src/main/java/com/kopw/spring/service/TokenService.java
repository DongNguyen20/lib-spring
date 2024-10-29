package com.kopw.spring.service;

import com.kopw.spring.model.entity.TokenEntity;
import com.kopw.spring.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    @Transactional
    public String generateAccessToken(UserDetails userDetails) {
        tokenRepository.invalidateTokensByUser(userDetails.getUsername());

        // Tạo access token mới và lưu vào database
        String newToken = jwtService.generateToken(userDetails);
        tokenRepository.save(TokenEntity.builder().token(newToken)
                .username(userDetails.getUsername()).build());
        return newToken;
    }

    @Transactional
    public String generateRefreshToken(UserDetails userDetails) {
        // Tạo refresh token mới và lưu vào database
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        tokenRepository.save(TokenEntity.builder().token(refreshToken)
                .username(userDetails.getUsername())
                .isFreshToken(true)
                .build());
        return refreshToken;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return jwtService.isTokenValid(token, userDetails) && !isTokenRevoked(token);
    }

    private boolean isTokenRevoked(String token) {
        return tokenRepository.findByToken(token)
                .map(TokenEntity::isRevoked)
                .orElse(true);
    }
}
