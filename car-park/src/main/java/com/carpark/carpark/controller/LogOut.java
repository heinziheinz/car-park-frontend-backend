package com.carpark.carpark.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

@RestController
@RequestMapping("logout")
public class LogOut {
    @GetMapping
    void LogOutUser(HttpServletResponse response) {
        // Create a new cookie with the same name and set its max age to 0
        Cookie cookie = new Cookie("Bearer", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
