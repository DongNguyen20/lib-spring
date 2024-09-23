package com.lib.restfulspring.controller;

import com.lib.restfulspring.dto.UserCreateRequest;
import com.lib.restfulspring.dto.UserInfoDto;
import com.lib.restfulspring.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserInfoDto> getUserInfos() {
        return userService.getUserInfos();
    }

    @GetMapping("/paging")
    public List<UserInfoDto> getUserInfos(@PageableDefault Pageable pageable) {
        return userService.getUserInfos(pageable);
    }

    @PostMapping
    public UserInfoDto createUser(@RequestBody @Valid UserCreateRequest request) {
        return userService.createUser(request);
    }
}
