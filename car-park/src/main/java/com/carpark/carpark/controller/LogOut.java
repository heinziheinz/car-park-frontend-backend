package com.carpark.carpark.controller;


import com.carpark.carpark.model.SuccessfullyLogOut;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

@RestController
@RequestMapping("user-logout")
public class LogOut {
    @GetMapping
    SuccessfullyLogOut LogOutUser(HttpServletResponse response) {
        Cookie cookie = new Cookie("Bearer", "");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return new SuccessfullyLogOut("Logged Out");
    }
}
