package com.carpark.carpark.service;

import com.carpark.carpark.controller.RescourceNotFoundException;
import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.Reservation;
import com.carpark.carpark.model.User;
import com.carpark.carpark.repository.CarRepository;
import com.carpark.carpark.repository.ReservationRepository;
import com.carpark.carpark.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;


@Component
public class CarReservationService {

    private final DateTimeService dateTimeService;

    public CarReservationService(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    public Car getRequestedCar(long carId, CarRepository carRepository) throws RescourceNotFoundException {
        return carRepository.findById(carId).orElseThrow(RescourceNotFoundException::new);
    }

    public User getUser(long userId, UserRepository userRepository) throws RescourceNotFoundException {
        return userRepository.findById(userId).orElseThrow(RescourceNotFoundException::new);
    }

    public boolean isCarAvailableDuringTimePeriod(Car car, LocalDate startDate, LocalDate endDate) {

        return car.getReservations().isEmpty() || car.getReservations().stream()
                .anyMatch(
                        reservation -> reservation.getStartDate().isAfter(dateTimeService.getCurrentDate()) &&
                                reservation.getStartDate().isAfter(endDate) || reservation.getEndDate().isBefore(startDate)
                );

    }

    public void carGetsReserved(Car car, User user, LocalDate startDate, LocalDate endDate, ReservationRepository reservationRepository) {

        Reservation reservation = new Reservation(user, startDate, endDate);
        reservation.setCar(car); // Set the car for the reservation
        Reservation reservationInst = reservationRepository.save(reservation);
        Set<Reservation> reservations = car.getReservations();
        reservations.add(reservationInst);
    }
}
