package com.carpark.carpark.controller;


import com.carpark.carpark.model.Reservation;
import com.carpark.carpark.repository.ReservationRepository;
import com.carpark.carpark.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

        return reservationService.findAllReservationEntry(pageable);
    }

    @GetMapping("/id/{id}")
    Reservation findById(@PathVariable long id) throws RescourceNotFoundException {
        return reservationService.findReservationByIdEntry(id);
    }


    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) {
        reservationService.deleteReservationEntry(id);
    }

    @PutMapping("/{id}")
    Reservation update(@PathVariable long id, @RequestBody Reservation updatedReservation) throws RescourceNotFoundException {
        return reservationService.updateReservationEntry( id,  updatedReservation);
    }

}
