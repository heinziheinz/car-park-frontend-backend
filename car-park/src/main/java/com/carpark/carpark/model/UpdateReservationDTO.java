package com.carpark.carpark.model;

import java.time.LocalDate;

public record UpdateReservationDTO(LocalDate startDate, LocalDate endDate) {

}
