package com.carpark.carpark.service;

import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.CarHouse;
import com.carpark.carpark.model.CarPool;
import com.carpark.carpark.repository.CarHouseRepository;
import com.carpark.carpark.repository.CarPoolRepository;
import com.carpark.carpark.repository.CarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CarHouseServiceTest {
    CarHouseRepository mockCarHouseRepository = mock(CarHouseRepository.class);
    CarPoolRepository mockCarPoolRepository = mock(CarPoolRepository.class);
    CarRepository mockCarRepository = mock(CarRepository.class);

    @Test
    void removeCarFromCarHouse() {
        CarHouseService carHouseService = new CarHouseService(mockCarHouseRepository, mockCarPoolRepository, mockCarRepository);
        //( String houseName, String address, long capacity)
        CarHouse carHouse = new CarHouse("CarHouse", "Kandlgasse 15", 200);
        CarPool carPool = new CarPool("CarPool", "Kandlgasse 4", 500);
        Car car1 = new Car(1, "Honda", 200.0, "Image");
        Car car2 = new Car(2, "Toyota", 200.0, "Image");
        Car car3 = new Car(3, "Subaru", 200.0, "Image");
        Set<Car> cars = Set.of(car1, car2, car3);
        Set<Car> carsExpected = Set.of(car2, car3);
        carHouse.setCars(cars);

        CarHouse carHouseExpected = new CarHouse("CarHouse", "Kandlgasse 15", 200);
        carHouseExpected.setCars(carsExpected);

        long carHouseId = 1;
        long carPoolId = 1;

        when(mockCarHouseRepository.findById(carHouseId)).thenReturn(Optional.of(carHouse));
        when(mockCarPoolRepository.findById(carPoolId)).thenReturn(Optional.of(carPool));
        when(mockCarRepository.findById(carPoolId)).thenReturn(Optional.of(car1));
        when(mockCarHouseRepository.save(carHouse)).thenReturn(carHouse);
        when(mockCarPoolRepository.save(carPool)).thenReturn(carPool);

        CarHouse actual = carHouseService.removeCarFromCarHouse(1, 1, 1);
        Assertions.assertEquals(carHouseExpected, actual);
    }

    @Test
    void removeCar() {
        CarHouseService carHouseService = new CarHouseService(mockCarHouseRepository, mockCarPoolRepository, mockCarRepository);
        CarHouse carHouse = new CarHouse("CarHouse", "Kandlgasse 15", 200);
        Car car1 = new Car(1, "Honda", 200.0, "Image");
        Car car2 = new Car(2, "Toyota", 200.0, "Image");
        Car car3 = new Car(3, "Subaru", 200.0, "Image");
        Set<Car> cars = Set.of(car1, car2, car3);
        Set<Car> carsExpected = Set.of(car2, car3);
        carHouse.setCars(cars);
        CarHouse carHouseExpected = new CarHouse("CarHouse", "Kandlgasse 15", 200);
        carHouseExpected.setCars(carsExpected);
        long carHouseId = 1;
        long carID = 1;
        when(mockCarHouseRepository.findById(carHouseId)).thenReturn(Optional.of(carHouse));
        when(mockCarHouseRepository.save(carHouse)).thenReturn(carHouse);

        CarHouse actual = carHouseService.removeCar(carHouseId, carID);

        Assertions.assertEquals(carHouseExpected, actual);
    }
}