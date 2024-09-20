package com.lib.restfulspring.service;

import com.lib.restfulspring.dto.UserCreateRequest;
import com.lib.restfulspring.dto.UserInfoDto;

import java.util.List;

public interface UserService {

    List<UserInfoDto> getUserInfos();

    UserInfoDto createUser(UserCreateRequest request);
}
