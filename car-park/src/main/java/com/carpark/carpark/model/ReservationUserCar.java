package com.carpark.carpark.model;

import java.util.List;
import java.util.Set;

public record ReservationUserCar(User user, List<Reservation> reservation, Set<Car> car) {
}
