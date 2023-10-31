package com.carpark.carpark.controller;

import com.carpark.carpark.service.JWTGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("login")
public class LogIn {

    private final JWTGenerator jwtGenerator;


    public LogIn(JWTGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @GetMapping
    String jwt(Authentication authentication ){
        return jwtGenerator.generate(authentication);
    }
}
