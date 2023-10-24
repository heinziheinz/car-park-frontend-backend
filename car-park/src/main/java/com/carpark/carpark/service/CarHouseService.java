package com.carpark.carpark.service;

import com.carpark.carpark.controller.RescourceNotFoundException;
import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.CarHouse;
import com.carpark.carpark.model.CarPool;
import com.carpark.carpark.repository.CarHouseRepository;
import com.carpark.carpark.repository.CarPoolRepository;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CarHouseService {
    private final CarHouseRepository carHouseRepository;
    private final CarPoolRepository carPoolRepository;

    public CarHouseService(CarHouseRepository carHouseRepository, CarPoolRepository carPoolRepository) {
        this.carHouseRepository = carHouseRepository;
        this.carPoolRepository = carPoolRepository;
    }

    private CarHouse findById(long id) throws RescourceNotFoundException {
        return carHouseRepository.findById(id).orElseThrow(RescourceNotFoundException::new);
    }

    private CarPool findByIdCarPool(long id) throws RescourceNotFoundException {
        return carPoolRepository.findById(id).orElseThrow(RescourceNotFoundException::new);
    }

    private Set<Car> getCarsFromCarHouse(CarHouse carHouse) {
        return carHouse.getCars();
    }

    private void setCarDatabaseRelationships(Set<Car> carHouseCars, CarPool carPool) {
        carHouseCars.forEach((car) -> {
            car.setCarHouse(null);
            car.setCarPool(carPool);
        });
    }

    private Set<Car> getCarForCarPools(CarPool carPool) {
        return carPool.getCars();
    }

    private void addCarHouseCarsToCarPool(Set<Car> carPoolCars, Set<Car> carHouseCars) {
        carPoolCars.addAll(carHouseCars);
    }

    private void carPoolSetCars(CarPool carPool, Set<Car> carPoolCars) {
        carPool.setCars(carPoolCars);
    }

    private CarPool saveCarPool(CarPool carPool) {
        return carPoolRepository.save(carPool);
    }
    private void deleteCarHouseById(long id){
        carHouseRepository.deleteById(id);
    }

    public void deleteCarHouse(long id) throws RescourceNotFoundException {
        CarHouse carHouse = findById(id);

        long carPoolID = 1;
        CarPool carPool = findByIdCarPool(carPoolID);
        Set<Car> carHouseCars = getCarsFromCarHouse(carHouse);
        setCarDatabaseRelationships(carHouseCars, carPool);
        Set<Car> carPoolCars = getCarForCarPools(carPool);
        addCarHouseCarsToCarPool(carPoolCars, carHouseCars);
        carPoolSetCars(carPool, carPoolCars);
        saveCarPool(carPool);
        deleteCarHouseById(id);
    }
}
