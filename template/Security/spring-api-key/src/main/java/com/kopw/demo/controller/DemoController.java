package com.kopw.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/secure-data")
    public String getSecureData() {
        return "This is secure data accessible only with a valid API Key.";
    }
}