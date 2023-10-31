package com.carpark.carpark.controller;


import com.carpark.carpark.model.User;
import com.carpark.carpark.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
        System.out.println("user SOSOSO = " + user);
        
        return userService.saveUserEntry(user);
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

}
