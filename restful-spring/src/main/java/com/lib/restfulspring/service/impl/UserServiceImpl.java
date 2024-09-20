package com.lib.restfulspring.service.impl;

import com.lib.restfulspring.dto.UserCreateRequest;
import com.lib.restfulspring.dto.UserInfoDto;
import com.lib.restfulspring.entity.UserEntity;
import com.lib.restfulspring.exception.ApplicationException;
import com.lib.restfulspring.mapper.UserMapper;
import com.lib.restfulspring.repository.UserRepository;
import com.lib.restfulspring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserInfoDto> getUserInfos() {
        return userRepository.findAll().stream().map(userMapper::toUserInfoDto).toList();
    }

    @Override
    public UserInfoDto createUser(UserCreateRequest request) {
        var isExistUserName = userRepository.existsByUsername(request.getUsername());

        if (isExistUserName) {
            throw new ApplicationException("Username already exists");
        }
        var user = UserEntity.builder().username(request.getUsername())
                .password(request.getPassword()).build();
        userRepository.save(user);
        return userMapper.toUserInfoDto(user);
    }
}
