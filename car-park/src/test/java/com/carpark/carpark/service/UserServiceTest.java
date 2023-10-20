package com.carpark.carpark.service;

import com.carpark.carpark.controller.RescourceNotFoundException;
import com.carpark.carpark.model.User;
import com.carpark.carpark.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    UserRepository mockedUserRepository = mock(UserRepository.class);
    UserService userService = new UserService(mockedUserRepository);

    @Test
    void updateExistingUser() throws RescourceNotFoundException {
//        String name, LocalDate birthdate, String address
        User updateForUser = new User("Franz", LocalDate.of(2077, 1, 4), "Haidengasse 2");
        User user = new User("Karl", LocalDate.of(2077, 1, 4), "Haidengasse 2");
        Optional<User> existingUser = Optional.of(user);
        when(mockedUserRepository.save(existingUser.get())).thenReturn(updateForUser);

        User actual = userService.updateExistingUser(updateForUser, existingUser);

        Assertions.assertEquals(updateForUser, actual);

        Optional<User> emptyUser = Optional.empty();
        Assertions.assertThrows(RescourceNotFoundException.class, () -> {
            userService.updateExistingUser(updateForUser, emptyUser);
        });
    }
}