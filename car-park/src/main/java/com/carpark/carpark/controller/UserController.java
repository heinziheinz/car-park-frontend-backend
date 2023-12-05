package com.carpark.carpark.controller;


import com.carpark.carpark.model.User;
import com.carpark.carpark.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping
    Page<User> findAll(Pageable pageable) {
        return userService.findAllUsersEntry(pageable);
    }

    @GetMapping("/name/{name}")
    List<User> findAllByName(@PathVariable String name) {

        return userService.findByNameEntry(name);
    }

    @PostMapping
    User save(@RequestBody User user) {
        return userService.saveUserEntry(user, passwordEncoder);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) throws RescourceNotFoundException {
        userService.deleteUserEntry(id);
    }

    @GetMapping("/id/{id}")
    User findById(@PathVariable long id) throws RescourceNotFoundException {
        return userService.findByIdEntry(id);
    }

    @PutMapping("/{id}")
    User update(@PathVariable long id, @RequestBody User updatedUser) throws RescourceNotFoundException {
        return userService.updateExistingUser(updatedUser, id);
    }

    @GetMapping("get-user")
    String customized(Authentication authentication){
        return "hallo " + authentication.getName();
    }

}
