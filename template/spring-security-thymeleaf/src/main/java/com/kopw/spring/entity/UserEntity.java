package com.kopw.spring.entity;

import jakarta.persistence.*;
import lombok.*;

import static com.kopw.spring.common.AppConstants.USERS;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = USERS)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private String role;
}
