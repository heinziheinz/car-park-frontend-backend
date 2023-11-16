package com.carpark.carpark.service;

import com.carpark.carpark.model.Reservation;
import com.carpark.carpark.model.User;
import com.carpark.carpark.repository.ReservationRepository;
import com.carpark.carpark.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public UserService(UserRepository userRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }


    private User saveUser(User user) {
        return userRepository.save(user);
    }

    private void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    private void deleteSeveralReservations(List<Reservation> reservations) {
        reservationRepository.deleteAll(reservations);
    }

    public Page<User> findAllUsersEntry(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public List<User> findByNameEntry(String name) {
        return userRepository.findAllByName(name);
    }

    public User saveUserEntry(User user) {
        System.out.println("user.getAuthorities()");
        System.out.println(user);
        System.out.println(user.getAuthorities() == null);
        if (user.getAuthorities() == null) {
            user.setAuthorities(Set.of("USER"));
        }
        return saveUser(user);
    }

    public void deleteUserEntry(long id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        List<Reservation> reservations = reservationRepository.findByUser(user);
        deleteSeveralReservations(reservations);
        deleteUser(user.getId());
    }

    public User findByIdEntry(long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    private void setNameBirthdateAddress(User user, User updatedUser) {
        user.setName(updatedUser.getName());
        user.setBirthdate(updatedUser.getBirthdate());
        user.setAddress(updatedUser.getAddress());
        user.setAuthorities(updatedUser.getAuthorities());
    }


    public User updateExistingUser(User updatedUser, long id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        setNameBirthdateAddress(user, updatedUser);
        return saveUser(user);
    }
}
