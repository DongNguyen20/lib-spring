package com.lib.restfulspring.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserInfoDto {
    private Long id;
    private String username;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
