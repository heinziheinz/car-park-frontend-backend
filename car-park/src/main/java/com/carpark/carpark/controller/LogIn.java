package com.carpark.carpark.controller;

import com.carpark.carpark.model.LogInUserJWT;
import com.carpark.carpark.model.User;
import com.carpark.carpark.repository.UserRepository;
import com.carpark.carpark.service.JWTGenerator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("login")
public class LogIn {

    private final UserRepository userRepository;


    private final JWTGenerator jwtGenerator;


    public LogIn(UserRepository userRepository, JWTGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
    }

    @GetMapping
    LogInUserJWT jwt(Authentication authentication, HttpServletResponse response, HttpServletRequest request) {
        String jwt = jwtGenerator.generate(authentication);

        System.out.println("request.getLocalName(); " + request.getLocalName());
        System.out.println("request.getLocalAddr(); " + request.getLocalAddr() );
        Cookie cookie = new Cookie("Bearer", jwt);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        response.addCookie(cookie);
        User user = userRepository.findByName(authentication.getName()).orElseThrow(EntityNotFoundException::new);
        return new LogInUserJWT(user.getId(), user.getName(), user.getAuthorities());
    }
}
