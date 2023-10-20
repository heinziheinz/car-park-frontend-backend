package com.carpark.carpark.controller;


import com.carpark.carpark.model.Reservation;
import com.carpark.carpark.repository.ReservationRepository;
import com.carpark.carpark.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("reservation")
public class ReservationController {
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    public ReservationController(ReservationRepository reservationRepository, ReservationService reservationService) {
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
    }

    @GetMapping
    Page<Reservation> findAll(Pageable pageable) {
        return reservationRepository.findAll(pageable);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) {
        reservationRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    Reservation update(@PathVariable long id, @RequestBody Reservation updatedReservation) throws RescourceNotFoundException {
        Optional<Reservation> existingReservation = reservationRepository.findById(id);
        return reservationService.updateExistingReservation(existingReservation, updatedReservation);
    }

}
