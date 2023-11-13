package com.carpark.carpark.service;

import com.carpark.carpark.controller.RescourceNotFoundException;
import com.carpark.carpark.model.*;
import com.carpark.carpark.repository.CarRepository;
import com.carpark.carpark.repository.ReservationRepository;
import com.carpark.carpark.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, CarRepository carRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }


    private Page<Reservation> findAllReservation(Pageable pageable) {
        return reservationRepository.findAll(pageable);
    }

    private ReservationWithCar findReservationWithCarById(long id) throws RescourceNotFoundException {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(RescourceNotFoundException::new);
        return new ReservationWithCar(reservation.getId(), reservation.getStartDate(), reservation.getEndDate(), reservation.getCar(), reservation.getCar().getCarHouse());
    }

    private Reservation findReservationById(long id) throws RescourceNotFoundException {
        return reservationRepository.findById(id).orElseThrow(RescourceNotFoundException::new);
    }

    private void deleteReservation(long id) {
        reservationRepository.deleteById(id);
    }

    private void updateStartDateAndEndDate(Reservation reservation, Reservation updatedReservation) {

        reservation.setStartDate(updatedReservation.getStartDate());
        reservation.setEndDate(updatedReservation.getEndDate());
    }

    private Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Page<Reservation> findAllReservationEntry(Pageable pageable) {
        return findAllReservation(pageable);
    }

    public ReservationWithCar findReservationByIdEntry(long id) throws RescourceNotFoundException {
        return findReservationWithCarById(id);
    }


    public void deleteReservationEntry(long id) throws RescourceNotFoundException {
        deleteReservation(id);
    }


    public Reservation updateReservationEntry(
            long id,
            Reservation updatedReservation) throws RescourceNotFoundException {

        Reservation reservation = findReservationById(id);
        updateStartDateAndEndDate(reservation, updatedReservation);
        return saveReservation(reservation);

    }

    private List<Car> getCarsFromReservations(List<Reservation> reservations) {
        return reservations.stream().map(Reservation::getCar).toList();
    }

    public List<Car> getAllReservedCars(long id) throws RescourceNotFoundException {
        User user = userRepository.findById(id).orElseThrow(RescourceNotFoundException::new);
        List<Reservation> reservations = reservationRepository.findByUser(user);
        return getCarsFromReservations(reservations);
    }

    private Set<Car> getSetOfCars(List<Reservation> reservations) {
        Set<Car> reservedCars = new HashSet<>();
        reservations.forEach((reservation -> {
            reservedCars.add(reservation.getCar());
        }));
        return reservedCars;

    }

    private List<ReservationsComplete> getReservationComplete(User user, List<Reservation> reservations) {
        List<ReservationsComplete> reservationsCompletes = new ArrayList<>();
        reservations.forEach((reservation -> {
            reservationsCompletes.add(new ReservationsComplete(user, reservation.getCar(), reservation.getStartDate(), reservation.getEndDate(), reservation.getCar().getCarHouse(), reservation.getId()));
        }));
        return reservationsCompletes;
    }

    public Page<ReservationsComplete> getAllReservations(long id, Pageable pageable) throws RescourceNotFoundException {

        User user = userRepository.findById(id).orElseThrow(RescourceNotFoundException::new);
        Page<Reservation> reservationsPage = reservationRepository.findAllByUser(user, pageable);
        List<ReservationsComplete> reservationsCompletes = getReservationComplete(user, reservationsPage.getContent());
        return new PageImpl<>(reservationsCompletes, pageable, reservationsPage.getTotalElements());
    }

}
