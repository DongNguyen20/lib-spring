package com.kopw.spring.service;

import com.kopw.spring.entity.UserEntity;
import com.kopw.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(String username, String rawPassword, String role) {
        UserEntity user = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .role(role).build();
        userRepository.save(user);
    }
}
