package com.carpark.carpark.controller;


import com.carpark.carpark.model.User;
import com.carpark.carpark.repository.UserRepository;
import com.carpark.carpark.service.ReservationService;
import com.carpark.carpark.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService =  userService;
    }

//    @GetMapping
//    List<User> findAll() {
//        return userRepository.findAll();
//    }

    @GetMapping
    Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @GetMapping("/name/{name}")
    List<User> findAllByType(@PathVariable String name) {
        return userRepository.findAllByName(name);
    }

    @PostMapping
    User save(@RequestBody User user) {
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/id/{id}")
    User findById(@PathVariable long id) throws RescourceNotFoundException {
        return userRepository.findById(id).orElseThrow(RescourceNotFoundException::new);
    }

    @PutMapping("/{id}")
    User update(@PathVariable long id, @RequestBody User updatedUser) throws RescourceNotFoundException {
        Optional<User> existingUser = userRepository.findById(id);

        return updateExistingUser(updatedUser, existingUser);
    }

    private User updateExistingUser(User updatedUser, Optional<User> existingUser) throws RescourceNotFoundException {
        return userService.updateExistingUser(updatedUser,existingUser);
    }

}
