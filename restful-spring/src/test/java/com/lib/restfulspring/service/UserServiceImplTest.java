package com.lib.restfulspring.service;

import com.lib.restfulspring.common.MessageHelper;
import com.lib.restfulspring.dto.UserCreateRequest;
import com.lib.restfulspring.dto.UserInfoDto;
import com.lib.restfulspring.entity.UserEntity;
import com.lib.restfulspring.exception.ApplicationException;
import com.lib.restfulspring.mapper.UserMapper;
import com.lib.restfulspring.repository.UserRepository;
import com.lib.restfulspring.service.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Locale;

import static com.lib.restfulspring.common.ErrorMessage.ITEM_ALREADY_EXISTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private MessageHelper messageHelper;

    private static UserInfoDto userInfo;

    @BeforeAll
    static void setup() {
        userInfo = UserInfoDto.builder().username("admin").build();
    }

    @Test
    void testGetUserInfosSuccess() {
        List<UserEntity> userEntities = List.of(UserEntity.builder().username("admin").build());
        when(userRepository.findAll()).thenReturn(userEntities);
        when(userMapper.toUserInfoDto(Mockito.any())).thenReturn(userInfo);
        var response = userService.getUserInfos();
        assertEquals(userInfo, response.get(0));
    }

    @Test
    void testGetUserInfosWithPageableSuccess() {
        List<UserEntity> userEntities = List.of(UserEntity.builder().username("admin").build());
        Page<UserEntity> mockPage = new PageImpl<>(userEntities);
        Pageable pageable = PageRequest.of(0, 2);

        when(userMapper.toUserInfoDto(Mockito.any())).thenReturn(userInfo);
        when(userRepository.findAll(Mockito.any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(mockPage);
        var response = userService.getUserInfos(pageable);
        assertNotNull(response);
        assertEquals(userInfo, response.get(0));
    }

    @Test
    void testCreateUserFailExistedUsername() {
        String userName = "admin";
        UserCreateRequest request = UserCreateRequest.builder().username(userName).password("1234").build();
        when(userRepository.existsByUsername(userName)).thenReturn(true);
        when(messageHelper.getMessage(ITEM_ALREADY_EXISTS, new String[]{userName}, new Locale("vi"))).thenReturn("item is already exists");
        assertThrows(ApplicationException.class, () -> userService.createUser(request));
    }

    @AfterAll
    static void cleanUp() {
        userInfo = null;
    }
}
