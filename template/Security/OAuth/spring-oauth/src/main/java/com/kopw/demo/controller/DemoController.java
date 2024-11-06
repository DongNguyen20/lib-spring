package com.kopw.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DemoController {
    private final OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping
    public String home(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return "Hello %s!".formatted(oAuth2User.getAttributes());
    }

    @DeleteMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            String userName = authentication.getName();
            // Xóa token OAuth khỏi bộ nhớ
            authorizedClientService.removeAuthorizedClient("github", userName);
            authorizedClientService.removeAuthorizedClient("google", userName);

            // Xóa session và xác thực
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "Đăng xuất thành công!";
    }
}
