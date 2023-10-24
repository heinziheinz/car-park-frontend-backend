package com.carpark.carpark.service;

import com.carpark.carpark.controller.RescourceNotFoundException;
import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.CarHouse;
import com.carpark.carpark.model.CarPool;
import com.carpark.carpark.repository.CarHouseRepository;
import com.carpark.carpark.repository.CarPoolRepository;
import com.carpark.carpark.repository.CarRepository;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CarHouseService {
    private final CarHouseRepository carHouseRepository;
    private final CarPoolRepository carPoolRepository;
    private final CarRepository carRepository;

    public CarHouseService(CarHouseRepository carHouseRepository, CarPoolRepository carPoolRepository, CarRepository carRepository) {
        this.carHouseRepository = carHouseRepository;
        this.carPoolRepository = carPoolRepository;
        this.carRepository = carRepository;
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

    private Set<Car> getCarsForCarPool(CarPool carPool) {
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

    private void deleteCarHouseById(long id) {
        carHouseRepository.deleteById(id);
    }

    private void setCarHouseValues(CarHouse carHouse, CarHouse updatedCarHouse) {
        carHouse.setHouseName(updatedCarHouse.getHouseName());
        carHouse.setCapacity(updatedCarHouse.getCapacity());
        carHouse.setAddress(updatedCarHouse.getAddress());
    }

    private CarHouse saveCarHouse(CarHouse carHouse) {
        return carHouseRepository.save(carHouse);
    }

    private Car findCarById(long id) throws RescourceNotFoundException {
        return carRepository.findById(id)
                .orElseThrow(RescourceNotFoundException::new);
    }

    private CarPool findCarPoolById(long id) throws RescourceNotFoundException {
        return carPoolRepository.findById(id).orElseThrow(RescourceNotFoundException::new);
    }

    private void addCarToCarHouse(CarHouse carHouse, Car car) {
        carHouse.getCars().add(car);
        car.setCarHouse(carHouse);
    }

    private void removeCarFromArray(Set<Car> cars, Car car) {
        cars.remove(car);
    }

    private void changeReference(CarPool carPool, Set<Car> cars, Car car) {
        carPool.setCars(cars);
        car.setCarPool(null);
    }

    private Car saveCar(Car car) {
        return carRepository.save(car);
    }


    private Set<Car> filterCars(CarHouse carHouse, long carId) {
        return carHouse.getCars().stream()
                .filter((car) -> {
                    return car.getId() != carId;
                }).collect(Collectors.toSet());
    }

    private void setCarsInCarHouse(CarHouse carHouse, Set<Car> removedCars) {
        carHouse.setCars(removedCars);
    }

    private void deleteCarHouseSetAndSetCarPoolCarSet(Car addedCarToCarPool, CarPool carPool){

        addedCarToCarPool.setCarHouse(null);
        addedCarToCarPool.setCarPool(carPool);
    }

    private void addCarToCarPool(Set<Car> carPoolCars, Car addedCarToCarPool){
        carPoolCars.add(addedCarToCarPool);
    }

    public void deleteCarHouse(long id) throws RescourceNotFoundException {
        CarHouse carHouse = findById(id);

        long carPoolID = 1;
        CarPool carPool = findByIdCarPool(carPoolID);
        Set<Car> carHouseCars = getCarsFromCarHouse(carHouse);
        setCarDatabaseRelationships(carHouseCars, carPool);
        Set<Car> carPoolCars = getCarsForCarPool(carPool);
        addCarHouseCarsToCarPool(carPoolCars, carHouseCars);
        carPoolSetCars(carPool, carPoolCars);
        saveCarPool(carPool);
        deleteCarHouseById(id);
    }

    public CarHouse update(long id, CarHouse updatedCarHouse) throws RescourceNotFoundException {
        CarHouse carHouse = findById(id);
        setCarHouseValues(carHouse, updatedCarHouse);

        return saveCarHouse(carHouse);
    }

    public CarHouse addCarToCarHouse(long carhouseId, long carId, long carPoolId) throws RescourceNotFoundException {
        CarHouse carHouse = findById(carhouseId);
        Car car = findCarById(carId);
        CarPool carPool = findCarPoolById(carPoolId);
        addCarToCarHouse(carHouse, car);
        saveCarHouse(carHouse);
        Set<Car> cars = getCarsForCarPool(carPool);
        removeCarFromArray(cars, car);
        changeReference(carPool, cars, car);
        saveCar(car);
        saveCarPool(carPool);


        return carHouse;
    }

    public Car removeCarFromCarHouse(long carhouseId, long carPoolId, long carId) throws RescourceNotFoundException {
        CarHouse carHouse = findById(carhouseId);
        CarPool carPool = findByIdCarPool(carPoolId);
        Set<Car> removedCars = filterCars(carHouse, carId);
        setCarsInCarHouse(carHouse, removedCars);
        Car addedCarToCarPool = findCarById(carId);
        Set<Car> carPoolCars = getCarsForCarPool(carPool);
        deleteCarHouseSetAndSetCarPoolCarSet(addedCarToCarPool, carPool);
        addCarToCarPool(carPoolCars, addedCarToCarPool);
        carPool.setCars(carPoolCars);
        saveCarHouse(carHouse);
        saveCarPool( carPool);


        return addedCarToCarPool;
    }
}
