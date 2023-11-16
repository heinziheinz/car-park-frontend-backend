package com.carpark.carpark.service;

import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.Reservation;
import com.carpark.carpark.model.User;
import com.carpark.carpark.repository.*;
import com.carpark.carpark.service.checkcaravailablility.CarAvailable;
import com.carpark.carpark.service.checkcaravailablility.CheckCarAvailabilitySQLQuery;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

class CarReservationServiceTest {


    DateTimeService mockDateTimeService = mock(DateTimeService.class);
    ReservationRepository mockreservationRepository = mock(ReservationRepository.class);
    CarRepository mockCarRepository = mock(CarRepository.class);
    CarPoolRepository mockCarPoolRepository = mock(CarPoolRepository.class);
    UserRepository userRepository = mock(UserRepository.class);
    CarHouseRepository mockCarHouseRepository = mock(CarHouseRepository.class);

    CarHouseService mockCarHouseService = mock(CarHouseService.class);
    CarPoolService mockCarPoolService = mock(CarPoolService.class);

    List<CarAvailable> mockCheckCarAvailability = List.of(mock(CheckCarAvailabilitySQLQuery.class));
    Pageable pageable = mock(Pageable.class);
    EntityNotFoundException entityNotFoundException = mock(EntityNotFoundException.class);

    CarReservationService carReservationService = new CarReservationService(mockDateTimeService, mockreservationRepository, mockCarRepository, mockCarPoolRepository, userRepository, mockCarHouseRepository, mockCheckCarAvailability, mockCarHouseService, mockCarPoolService);
    LocalDate mockStartDate = mock(LocalDate.class);
    LocalDate mockEndDate = mock(LocalDate.class);

    public static Stream<Arguments> arguments() {

        Car car1 = new Car(0, "Mercedes", 200.00, "image");
        Set<Reservation> reservationOne = Set.of();
        car1.setReservations(reservationOne);

        //empty array = true

        User user2 = new User("Karl", LocalDate.of(2022, 1, 1), "Hausgasse", "password", Set.of("USER", "ADMIN"));
        Reservation reservation2 = new Reservation(user2, LocalDate.of(2023, 10, 22), LocalDate.of(2023, 10, 24));
        Car car2 = new Car(0, "Mercedes", 200.00, "image");
        Set<Reservation> reservationTwo = Set.of(reservation2);
        car2.setReservations(reservationTwo);

        //
        User user3 = new User("Karl", LocalDate.of(2022, 1, 1), "Hausgasse", "password", Set.of("USER", "ADMIN"));
        Reservation reservation3 = new Reservation(user3, LocalDate.of(2022, 12, 29), LocalDate.of(2022, 12, 31));
        Car car3 = new Car(0, "Mercedes", 200.00, "image");
        Set<Reservation> reservationThree = Set.of(reservation3);
        car3.setReservations(reservationThree);

        User user4 = new User("Karl", LocalDate.of(2022, 1, 1), "Hausgasse", "password", Set.of("USER", "ADMIN"));
        Reservation reservation4 = new Reservation(user4, LocalDate.of(2024, 12, 20), LocalDate.of(2024, 12, 29));
        Car car4 = new Car(0, "Mercedes", 200.00, "image");
        Set<Reservation> reservationFour = Set.of(reservation4);
        car4.setReservations(reservationFour);

        User user5 = new User("Karl", LocalDate.of(2022, 1, 1), "Hausgasse", "password", Set.of("USER", "ADMIN"));
        Reservation reservation5 = new Reservation(user5, LocalDate.of(2024, 12, 20), LocalDate.of(2024, 12, 29));
        Car car5 = new Car(0, "Mercedes", 200.00, "image");
        Set<Reservation> reservationFive = Set.of(reservation5);
        car5.setReservations(reservationFive);

        User user6 = new User("Karl", LocalDate.of(2022, 1, 1), "Hausgasse", "password", Set.of("USER", "ADMIN"));
        Reservation reservation6 = new Reservation(user6, LocalDate.of(2024, 12, 20), LocalDate.of(2024, 12, 29));
        Car car6 = new Car(0, "Mercedes", 200.00, "image");
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

    @Test
    void findAllPaginated() {
        carReservationService.findAllPaginated(pageable);
        Mockito.verify(mockCarRepository, times(1)).findAll(pageable);
    }

    @Test
    void findAllByTypeName() {
        String name = "Karl";
        carReservationService.findAllByTypeName(name);
        Mockito.verify(mockCarRepository).findAllByTypeName(name);
    }

    @Test
    void findCarById() {
        //????
        long id = 1;
        Car car = new Car(1, "hall", 200, "Image");
        when(mockCarRepository.findById(id)).thenReturn(Optional.of(car));
        carReservationService.findCarById(id);
        Mockito.verify(mockCarRepository).findById(id);

    }

    @Test
    void findAvailableCars() {
        carReservationService.findAvailableCars(mockStartDate, mockEndDate);
        Mockito.verify(mockCarRepository).findAvailableCars(mockStartDate, mockEndDate);
    }

    @Test
    void findAllCArsNotAllocatedToACarHouse() {
        carReservationService.findAllCArsNotAllocatedToACarHouse(pageable);
        Mockito.verify(mockCarRepository).findAllByCarHouseIsNull(pageable);
    }

    @Test
    void updateCar() {
        Car car = new Car(1, "Toyota", 200, "Image");
        Car updatedCar = new Car(2, "Honda", 250, "Image");
        long carId = 1;
        when(mockCarRepository.findById(carId)).thenReturn(Optional.of(car));
        when(mockCarRepository.save(car)).thenReturn(car);

        Car actual = carReservationService.updateCar(carId, updatedCar);

        Assertions.assertEquals(updatedCar.getTypeName(), actual.getTypeName());
        Assertions.assertEquals(updatedCar.getPrice(), actual.getPrice());
        Mockito.verify(mockCarRepository).save(car);
        Mockito.verify(mockCarRepository).findById(carId);

    }
}