package com.lib.restfulspring.service.impl;

import com.lib.restfulspring.common.MessageHelper;
import com.lib.restfulspring.dto.UserCreateRequest;
import com.lib.restfulspring.dto.UserInfoDto;
import com.lib.restfulspring.entity.UserEntity;
import com.lib.restfulspring.exception.ApplicationException;
import com.lib.restfulspring.mapper.UserMapper;
import com.lib.restfulspring.repository.UserRepository;
import com.lib.restfulspring.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

import static com.lib.restfulspring.common.ErrorMessage.ITEM_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MessageHelper messageHelper;

    @Override
    public List<UserInfoDto> getUserInfos() {
        return userRepository.findAll().stream().map(userMapper::toUserInfoDto).toList();
    }

    @Override
    public List<UserInfoDto> getUserInfos(Pageable pageable) {
        return userRepository.findAll(pageable).stream().map(userMapper::toUserInfoDto).toList();
    }

    @Override
    @Transactional
    public UserInfoDto createUser(UserCreateRequest request) {
        var isExistUserName = userRepository.existsByUsername(request.getUsername());

        if (isExistUserName) {
            throw new ApplicationException(messageHelper.getMessage(ITEM_ALREADY_EXISTS,
                    new String[]{request.getUsername()}, new Locale("vi")));
        }
        var user = UserEntity.builder().username(request.getUsername())
                .password(request.getPassword()).build();
        userRepository.save(user);
        return userMapper.toUserInfoDto(user);
    }
}
