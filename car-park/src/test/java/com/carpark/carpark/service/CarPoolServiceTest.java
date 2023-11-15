package com.carpark.carpark.service;

import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.CarPool;
import com.carpark.carpark.repository.CarPoolRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

class CarPoolServiceTest {
    CarPoolRepository mockCarPoolRepository = mock(CarPoolRepository.class);

    @Test
    void addCarToCarPool() {
        CarPoolService carPoolService = new CarPoolService(mockCarPoolRepository);
        CarPool carPool = new CarPool("CarPool", "Hermanngasse 2", 200);
        long carPoolId = 1;
        Car car = new Car(1,"Toyota", 200, "Image");
        CarPool carPoolExpected = new CarPool("CarPool", "Hermanngasse 2", 200);
        Set<Car> cars = Set.of(car);
        carPoolExpected.setCars(cars);
        when(mockCarPoolRepository.findById(carPoolId)).thenReturn(Optional.of(carPool));
        when(mockCarPoolRepository.save(carPool)).thenReturn(carPool);

        CarPool actual = carPoolService.addCarToCarPool(carPoolId, car);

        Assertions.assertEquals(carPoolExpected, actual);

    }
}