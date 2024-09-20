package com.lib.restfulspring.mapper;

import com.lib.restfulspring.dto.UserInfoDto;
import com.lib.restfulspring.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserInfoDto toUserInfoDto(UserEntity entity);
}
