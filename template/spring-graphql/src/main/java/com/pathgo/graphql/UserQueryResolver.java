package com.pathgo.graphql;

import com.pathgo.model.UserEntity;
import com.pathgo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserQueryResolver {
    private final UserRepository userRepository;

    @QueryMapping
    public UserEntity getUser(@Argument("id") Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
