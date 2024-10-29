package com.kopw.spring.repository;

import com.kopw.spring.model.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    List<TokenEntity> findByUsernameAndIsRevokedFalse(String username);

    // Tìm kiếm một token cụ thể để kiểm tra trạng thái của nó
    Optional<TokenEntity> findByToken(String token);

    // Thu hồi tất cả token của người dùng khi có một token mới được tạo
    default void invalidateTokensByUser(String username) {
        List<TokenEntity> tokens = findByUsernameAndIsRevokedFalse(username);
        tokens.forEach(token -> token.setRevoked(true));
        saveAll(tokens);
    }
}
