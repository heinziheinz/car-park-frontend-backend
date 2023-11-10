package com.carpark.carpark.model;

import java.time.LocalDate;

public record ReservationsComplete(User user,Car car, LocalDate startDate, LocalDate endDate, CarHouse carHouse) {
}
