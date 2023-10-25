package com.carpark.carpark.service;

import com.carpark.carpark.controller.RescourceNotFoundException;
import com.carpark.carpark.model.User;
import com.carpark.carpark.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private Page<User> finAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    private List<User> findByName(String name) {
        return userRepository.findAllByName(name);
    }

    private User saveUser(User user) {
        return userRepository.save(user);
    }

    private void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    private User findById(long id) throws RescourceNotFoundException {
        return userRepository.findById(id).orElseThrow(RescourceNotFoundException::new);
    }


    public Page<User> findAllUsersEntry(Pageable pageable) {
        return finAllUsers(pageable);
    }

    public List<User> findByNameEntry(String name) {
        return findByName(name);
    }

    public User saveUserEntry(User user) {
        return saveUser(user);
    }

    public void deleteUserEntry(long id) {
        deleteUser(id);
    }

    public User findByIdEntry(long id) throws RescourceNotFoundException {
        return findById(id);
    }

    private void setNameBirthdateAddress(User user, User updatedUser) {
        user.setName(updatedUser.getName());
        user.setBirthdate(updatedUser.getBirthdate());
        user.setAddress(updatedUser.getAddress());
    }


    public User updateExistingUser(User updatedUser, long id) throws RescourceNotFoundException {

        User user = findById(id);
        setNameBirthdateAddress(user, updatedUser);
        return saveUser(user);

    }

}
