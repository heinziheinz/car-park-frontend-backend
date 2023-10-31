package com.carpark.carpark.repository;

import com.carpark.carpark.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByName(String name);
    Optional<User> findByName(String username);
}
