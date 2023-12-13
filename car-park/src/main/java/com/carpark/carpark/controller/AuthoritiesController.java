package com.carpark.carpark.controller;


import com.carpark.carpark.model.Authority;
import com.carpark.carpark.repository.AuthorityRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("authorities")
public class AuthoritiesController {
    //
    private final AuthorityRepository authorityRepository;

    public AuthoritiesController(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @GetMapping
    List<Authority> findAll() {
      return authorityRepository.findAll();
    }
}
