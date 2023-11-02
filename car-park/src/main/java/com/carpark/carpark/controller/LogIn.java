package com.carpark.carpark.controller;

import com.carpark.carpark.model.LogInUserJWT;
import com.carpark.carpark.model.User;
import com.carpark.carpark.repository.UserRepository;
import com.carpark.carpark.service.JWTGenerator;
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
    LogInUserJWT jwt(Authentication authentication) throws RescourceNotFoundException{

        User user = userRepository.findByName(authentication.getName()).orElseThrow(RescourceNotFoundException::new);
        return new LogInUserJWT(jwtGenerator.generate(authentication), user.getId(), user.getName(), user.getAuthorities());
    }
}
