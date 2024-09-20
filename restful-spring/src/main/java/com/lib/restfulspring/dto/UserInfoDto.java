package com.lib.restfulspring.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoDto {
    private Long id;
    private String username;
}
