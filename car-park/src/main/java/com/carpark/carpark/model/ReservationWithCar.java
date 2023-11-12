package com.carpark.carpark.model;

import java.time.LocalDate;

public record ReservationWithCar(long id,LocalDate startDate, LocalDate endDate, Car car, CarHouse carHouse) {
}
