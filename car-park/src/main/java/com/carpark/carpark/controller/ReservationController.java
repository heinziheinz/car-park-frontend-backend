package com.carpark.carpark.controller;


import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.Reservation;
import com.carpark.carpark.model.ReservationWithCar;
import com.carpark.carpark.model.ReservationsComplete;
import com.carpark.carpark.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reservation")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    Page<Reservation> findAll(Pageable pageable) {

        return reservationService.findAllReservationEntry(pageable);
    }

    @GetMapping("/id/{id}")
    ReservationWithCar findById(@PathVariable long id) throws RescourceNotFoundException {
        return reservationService.findReservationByIdEntry(id);
    }


    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) {

        reservationService.deleteReservationEntry(id);
    }

    @PutMapping("/{id}")
    Reservation update(@PathVariable long id, @RequestBody Reservation updatedReservation) {
        return reservationService.updateReservationEntry(id, updatedReservation);
    }

    @GetMapping("/get-all-reserved-cars/id/{userId}")
    List<Car> getAllReservedCars(@PathVariable long userId) {
        return reservationService.getAllReservedCars(userId);
    }

    @GetMapping("/get-all-reserved-cars-reservations-user/id/{userId}")
    Page<ReservationsComplete> getAllReservedCarsUserReservations(
            @PathVariable long userId,
            Pageable pageable
    ) throws RescourceNotFoundException {
        return reservationService.getAllReservations(userId, pageable);

    }

}
