package com.lib.restfulspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lib.restfulspring.dto.UserCreateRequest;
import com.lib.restfulspring.dto.UserInfoDto;
import com.lib.restfulspring.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/users";

    @Test
    void testGetUserInfosSuccess() throws Exception {
        when(userService.getUserInfos()).thenReturn(new ArrayList<UserInfoDto>());
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUserInfosPagingSuccess() throws Exception {
        List<UserInfoDto> userInfos = List.of(UserInfoDto.builder().id(1L).username("admin").build(),
                UserInfoDto.builder().id(2L).username("kopw").build());
        when(userService.getUserInfos(PageRequest.of(0,10, Sort.by("username").descending())))
                .thenReturn(userInfos.stream().sorted(Comparator.comparing(UserInfoDto::getUsername).reversed())
                .toList());

        URI uri = UriComponentsBuilder.fromPath(BASE_URL + "/paging")
                .queryParam("page", 0)
                .queryParam("size", 10)
                .queryParam("sort", "username,desc")
                .build()
                .toUri();

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].username", is("kopw")))
                .andExpect(jsonPath("$[1].id", is(1)))
                .andExpect(jsonPath("$[1].username", is("admin")));
    }

    @Test
    void testCreateUserSuccess() throws Exception {
        UserCreateRequest request = UserCreateRequest.builder().username("admin").password("123456").build();
        UserInfoDto response = UserInfoDto.builder().id(1L).username("admin").build();
        when(userService.createUser(request)).thenReturn(response);
        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());
    }
}
