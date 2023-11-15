package com.carpark.carpark.service;

import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.CarHouse;
import com.carpark.carpark.model.CarPool;

public class TransferCars {
    private final CarHouseService carHouseService;
    private final CarPoolService carPoolService;

    private final CarReservationService carReservationService;


    public TransferCars(CarHouseService carHouseService, CarPoolService carPoolService, CarReservationService carReservationService) {
        this.carHouseService = carHouseService;
        this.carPoolService = carPoolService;
        this.carReservationService = carReservationService;

    }

    public void transferCarFromCarHouseToCarPool(long carHouseId, long carId, long carPoolId) {
        CarHouse carHouse = carHouseService.removeCar(carHouseId, carId);
        Car car = carReservationService.findCarById(carId);
        CarPool carPool = carPoolService.addCarToCarPool(carPoolId, car);
        carReservationService.deleteCarHouseSetAndSetCarPoolCarSet(car, carPool);
    }
}
