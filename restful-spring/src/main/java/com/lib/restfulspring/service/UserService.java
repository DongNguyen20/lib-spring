package com.lib.restfulspring.service;

import com.lib.restfulspring.dto.UserCreateRequest;
import com.lib.restfulspring.dto.UserInfoDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<UserInfoDto> getUserInfos();

    List<UserInfoDto> getUserInfos(Pageable pageable);

    UserInfoDto createUser(UserCreateRequest request);
}
