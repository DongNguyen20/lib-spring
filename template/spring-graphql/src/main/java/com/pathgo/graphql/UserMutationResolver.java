package com.pathgo.graphql;

import com.pathgo.model.UserEntity;
import com.pathgo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserMutationResolver {
    private final UserRepository userRepository;

    @MutationMapping
    public UserEntity addUser(@Argument String name, @Argument int age) {
        UserEntity user = UserEntity.builder()
                .name(name)
                .age(age)
                .build();
        return userRepository.save(user);
    }
}
