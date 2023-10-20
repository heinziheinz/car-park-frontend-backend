package com.carpark.carpark.service;

import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.Reservation;
import com.carpark.carpark.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CarReservationServiceTest {




    DateTimeService mockDateTimeService = mock(DateTimeService.class);
    CarReservationService carReservationService = new CarReservationService(mockDateTimeService);

    public static Stream<Arguments> arguments() {
        //empty array = true
        Car car1 = new Car("Mercedes", 200.00);
        Set<Reservation> reservationOne = Set.of();
        car1.setReservations(reservationOne);

        //empty array = true
        User user2 = new User("Karl", LocalDate.of(2022, 1, 1), "Hausgasse");
        Reservation reservation2 = new Reservation(user2, LocalDate.of(2023, 10, 22), LocalDate.of(2023, 10, 24));
        Car car2 = new Car("Mercedes", 200.00);
        Set<Reservation> reservationTwo = Set.of(reservation2);
        car2.setReservations(reservationTwo);

        //
        User user3 = new User("Karl", LocalDate.of(2022, 1, 1), "Hausgasse");
        Reservation reservation3 = new Reservation(user3, LocalDate.of(2022, 12, 29), LocalDate.of(2022, 12, 31));
        Car car3 = new Car("Mercedes", 200.00);
        Set<Reservation> reservationThree = Set.of(reservation3);
        car3.setReservations(reservationThree);

        User user4 = new User("Karl", LocalDate.of(2022, 1, 1), "Hausgasse");
        Reservation reservation4 = new Reservation(user4, LocalDate.of(2024, 12, 20), LocalDate.of(2024, 12, 29));
        Car car4 = new Car("Mercedes", 200.00);
        Set<Reservation> reservationFour = Set.of(reservation4);
        car4.setReservations(reservationFour);

        User user5 = new User("Karl", LocalDate.of(2022, 1, 1), "Hausgasse");
        Reservation reservation5 = new Reservation(user5, LocalDate.of(2024, 12, 20), LocalDate.of(2024, 12, 29));
        Car car5 = new Car("Mercedes", 200.00);
        Set<Reservation> reservationFive = Set.of(reservation5);
        car5.setReservations(reservationFive);

        User user6 = new User("Karl", LocalDate.of(2022, 1, 1), "Hausgasse");
        Reservation reservation6 = new Reservation(user6, LocalDate.of(2024, 12, 20), LocalDate.of(2024, 12, 29));
        Car car6 = new Car("Mercedes", 200.00);
        Set<Reservation> reservationSix = Set.of(reservation6);
        car6.setReservations(reservationSix);


        return Stream.of(
                Arguments.of(car1, LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 1), true),
                Arguments.of(car2, LocalDate.of(2023, 10, 25), LocalDate.of(2024, 10, 26), true),
                Arguments.of(car3, LocalDate.of(2023, 10, 17), LocalDate.of(2023, 10, 19), true),
                Arguments.of(car4, LocalDate.of(2024, 12, 29), LocalDate.of(2024, 12, 30), false),
                Arguments.of(car5, LocalDate.of(2024, 12, 19), LocalDate.of(2024, 12, 20), false),
                Arguments.of(car6, LocalDate.of(2024, 12, 19), LocalDate.of(2024, 12, 30), false)
        );
    }


    @ParameterizedTest
    @MethodSource("arguments")
    void isCarAvailableDuringTimePeriod(Car car, LocalDate startDate, LocalDate endDate, boolean expected) {
        LocalDate fixedDate = LocalDate.of(2023, 10, 18);
        when(mockDateTimeService.getCurrentDate()).thenReturn(fixedDate);

        boolean actual = carReservationService.isCarAvailableDuringTimePeriod(car, startDate, endDate);
        Assertions.assertEquals(expected, actual);
    }
}