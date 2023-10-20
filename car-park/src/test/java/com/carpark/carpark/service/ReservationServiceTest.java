package com.carpark.carpark.service;

import com.carpark.carpark.controller.RescourceNotFoundException;
import com.carpark.carpark.model.Reservation;
import com.carpark.carpark.repository.ReservationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReservationServiceTest {


    ReservationRepository mockReservationRepository = mock(ReservationRepository.class);
    ReservationService reservationService = new ReservationService(mockReservationRepository);

    @Test
    void updateExistingReservation() throws RescourceNotFoundException {

        Reservation reservation = new Reservation(null, LocalDate.of(2022, 2, 1), LocalDate.of(2023, 1, 1));
        Optional<Reservation> reservation1 = Optional.of(reservation);
        Reservation updatedREservation = new Reservation(null, LocalDate.of(2022, 2, 1), LocalDate.of(2023, 1, 1));
        when(mockReservationRepository.save(reservation1.get())).thenReturn(updatedREservation);

        Reservation actual = reservationService.updateExistingReservation( reservation1, updatedREservation);

        Assertions.assertEquals(updatedREservation, actual);


        Optional<Reservation> emptyReservation = Optional.empty();
        Assertions.assertThrows(RescourceNotFoundException.class, () -> {
            reservationService.updateExistingReservation( emptyReservation, updatedREservation);
        });
    }
}