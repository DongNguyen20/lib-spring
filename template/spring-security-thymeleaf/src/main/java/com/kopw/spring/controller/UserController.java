package com.kopw.spring.controller;

import com.kopw.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.kopw.spring.common.AppConstants.LOGIN;
import static com.kopw.spring.common.AppConstants.REGISTER;
import static com.kopw.spring.common.AppConstants.SLASH;
import static com.kopw.spring.common.AppConstants.USERS;


@Controller
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(SLASH + REGISTER)
    public String goRegisterPage() {
        return "user_register";
    }

    @PostMapping(SLASH + REGISTER)
    public String registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        userService.createUser(username, password, role);
        return LOGIN;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String userPage(Model model) {
        model.addAttribute("message", "Welcome to the User Page!");
        return "welcome";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("message", "Welcome to the Admin Page!");
        return "welcome";
    }

}