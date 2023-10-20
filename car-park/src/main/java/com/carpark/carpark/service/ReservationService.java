package com.carpark.carpark.service;

import com.carpark.carpark.controller.RescourceNotFoundException;
import com.carpark.carpark.model.Reservation;
import com.carpark.carpark.repository.ReservationRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation updateExistingReservation(
            Optional<Reservation> existingReservation,
            Reservation updatedReservation) throws RescourceNotFoundException {

        return getReservation(existingReservation, updatedReservation);
    }

    private Reservation getReservation( Optional<Reservation> existingReservation, Reservation updatedReservation) throws RescourceNotFoundException {
        if (existingReservation.isPresent()) {
            Reservation reservation = existingReservation.get();
            reservation.setStartDate(updatedReservation.getStartDate());
            reservation.setEndDate(updatedReservation.getEndDate());

            return reservationRepository.save(reservation);
        } else {
            throw new RescourceNotFoundException();
        }
    }
}
