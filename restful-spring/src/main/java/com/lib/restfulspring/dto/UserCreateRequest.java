package com.lib.restfulspring.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateRequest {
    @NotBlank(message = "username is not blank")
    private String username;

    @NotBlank(message = "password is not blank")
    private String password;
}
