package com.carpark.carpark.service.checkCarAvailablility;

import com.carpark.carpark.model.Car;
import com.carpark.carpark.repository.CarRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class CheckCarAvailabilitySQLQuery implements CarAvailable {
    private final CarRepository carRepository;

    public CheckCarAvailabilitySQLQuery(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public boolean isCarAvailable(Car car, LocalDate startDate, LocalDate endDate) {
        int overlappingCount = carRepository.countOverlappingReservations(car, startDate, endDate);
        return overlappingCount == 0;
    }
}
