package com.lib.restfulspring.service.impl;

import com.lib.restfulspring.dto.UserInfoDto;
import com.lib.restfulspring.mapper.UserMapper;
import com.lib.restfulspring.repository.UserRepository;
import com.lib.restfulspring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserInfoDto> getUserInfos() {
        return userRepository.findAll().stream().map(UserMapper::toUserInfoDto).collect(Collectors.toList());
    }
}
