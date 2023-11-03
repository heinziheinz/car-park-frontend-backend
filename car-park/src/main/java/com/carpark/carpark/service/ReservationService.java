package com.carpark.carpark.service;

import com.carpark.carpark.controller.RescourceNotFoundException;
import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.Reservation;
import com.carpark.carpark.model.User;
import com.carpark.carpark.repository.ReservationRepository;
import com.carpark.carpark.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }


    private Page<Reservation> findAllReservation(Pageable pageable) {
        return reservationRepository.findAll(pageable);
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

    public Reservation findReservationByIdEntry(long id) throws RescourceNotFoundException {
        return findReservationById(id);
    }


    public void deleteReservationEntry(long id) {
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

}
