package com.ambrogio.springbootauthuserapi.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/user/hello")
    public String userHello() {
        return "GREETINGS USER \uD83D\uDC64";
    }

    @GetMapping("/api/admin/hello")
    public String adminHello() {
        return "GREETINGS ADMIN \uD83D\uDEE1\uFE0F";
    }
    @GetMapping("/api/profile/ping")
    public String ping() {
        return "This works good";
    }
}
