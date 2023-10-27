package com.carpark.carpark.service.checkCarAvailablility;

import com.carpark.carpark.model.Car;

import java.time.LocalDate;

public interface CarAvailable {
    boolean isCarAvailable(Car car, LocalDate startDate, LocalDate endDate);
}
