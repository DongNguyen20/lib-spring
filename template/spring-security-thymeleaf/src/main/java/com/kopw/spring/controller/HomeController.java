package com.kopw.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.kopw.spring.common.AppConstants.HOME;
import static com.kopw.spring.common.AppConstants.LOGIN;
import static com.kopw.spring.common.AppConstants.SLASH;
import static org.springframework.security.config.Elements.LOGOUT;

@Controller
public class HomeController {

    @GetMapping(SLASH + LOGIN)
    public String login() {
        return LOGIN;
    }

    @GetMapping(SLASH + HOME)
    public String home() {
        return HOME;
    }

    @GetMapping(SLASH + LOGOUT)
    public String logout() {
        return LOGIN;
    }
}
