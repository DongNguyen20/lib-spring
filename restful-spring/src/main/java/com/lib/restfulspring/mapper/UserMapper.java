package com.lib.restfulspring.mapper;

import com.lib.restfulspring.dto.UserInfoDto;
import com.lib.restfulspring.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class UserMapper {
    public static UserInfoDto toUserInfoDto(UserEntity entity) {
        return UserInfoDto.builder()
               .id(entity.getId())
               .username(entity.getUsername())
               .build();
    }
}
