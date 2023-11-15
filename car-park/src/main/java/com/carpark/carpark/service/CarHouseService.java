package com.carpark.carpark.service;

import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.CarHouse;
import com.carpark.carpark.model.CarPool;
import com.carpark.carpark.model.DeletedCarHouse;
import com.carpark.carpark.repository.CarHouseRepository;
import com.carpark.carpark.repository.CarPoolRepository;
import com.carpark.carpark.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
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

    private void setCarDatabaseRelationships(Set<Car> carHouseCars, CarPool carPool) {
        carHouseCars.forEach((car) -> {
            car.setCarHouse(null);
            car.setCarPool(carPool);
        });
    }


    private void setCarHouseValues(CarHouse carHouse, CarHouse updatedCarHouse) {
        carHouse.setHouseName(updatedCarHouse.getHouseName());
        carHouse.setCapacity(updatedCarHouse.getCapacity());
        carHouse.setAddress(updatedCarHouse.getAddress());
    }


    private void addCarToCarHouse(CarHouse carHouse, Car car) {
        carHouse.getCars().add(car);
        car.setCarHouse(carHouse);
    }

    private void changeReference(CarPool carPool, Set<Car> cars, Car car) {
        carPool.setCars(cars);
        car.setCarPool(null);
    }

    //TODO: hier testen
    private Set<Car> filterCars(CarHouse carHouse, long carId) {
        return carHouse.getCars().stream()
                .filter((car) -> {
                    return car.getId() != carId;
                }).collect(Collectors.toSet());
    }


    private void deleteCarHouseSetAndSetCarPoolCarSet(Car addedCarToCarPool, CarPool carPool) {
        addedCarToCarPool.setCarHouse(null);
        addedCarToCarPool.setCarPool(carPool);
    }


    public DeletedCarHouse deleteCarHouse(long id) {
        CarHouse carHouse = carHouseRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        long carPoolID = 1;
        CarPool carPool = carPoolRepository.findById(carPoolID).orElseThrow(EntityNotFoundException::new);
        Set<Car> carHouseCars = carHouse.getCars();
        setCarDatabaseRelationships(carHouseCars, carPool);
        Set<Car> carPoolCars = carPool.getCars();
        carPoolCars.addAll(carHouseCars);
        carPool.setCars(carPoolCars);
        carPoolRepository.save(carPool);
        carHouseRepository.deleteById(id);
        return new DeletedCarHouse(carHouse.getId(), carHouse.getHouseName());
    }

    public CarHouse update(long id, CarHouse updatedCarHouse) {
        CarHouse carHouse = carHouseRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        setCarHouseValues(carHouse, updatedCarHouse);
        return carHouseRepository.save(carHouse);
    }

    public CarHouse addCarToCarHouse(long carHouseId, long carId, long carPoolId) {
        CarHouse carHouse = carHouseRepository.findById(carHouseId).orElseThrow(EntityNotFoundException::new);
        Car car = carRepository.findById(carId)
                .orElseThrow(EntityNotFoundException::new);
        CarPool carPool = carPoolRepository.findById(carPoolId).orElseThrow(EntityNotFoundException::new);
        addCarToCarHouse(carHouse, car);
        carHouseRepository.save(carHouse);
        Set<Car> cars = carPool.getCars();
        cars.remove(car);
        changeReference(carPool, cars, car);
        carRepository.save(car);
        carPoolRepository.save(carPool);
        return carHouse;
    }

    public Car removeCarFromCarHouse(long carHouseId, long carPoolId, long carId) {
        CarHouse carHouse = carHouseRepository.findById(carHouseId).orElseThrow(EntityNotFoundException::new);
        CarPool carPool = carPoolRepository.findById(carPoolId).orElseThrow(EntityNotFoundException::new);
        Set<Car> removedCars = filterCars(carHouse, carId);
        carHouse.setCars(removedCars);
        Car addedCarToCarPool = carRepository.findById(carId)
                .orElseThrow(EntityNotFoundException::new);
        Set<Car> carPoolCars = carPool.getCars();
        deleteCarHouseSetAndSetCarPoolCarSet(addedCarToCarPool, carPool);
        carPoolCars.add(addedCarToCarPool);
        carPool.setCars(carPoolCars);
        carHouseRepository.save(carHouse);
        carPoolRepository.save(carPool);
        return addedCarToCarPool;
    }

    public Page<CarHouse> findAllCarHousesEntry(Pageable pageable) {
        return carHouseRepository.findAll(pageable);
    }

    public CarHouse carHouseSaveEntry(CarHouse carHouse) {
        return carHouseRepository.save(carHouse);
    }

    public List<CarHouse> getAllCarHouses() {
        return carHouseRepository.findAll();
    }

    public CarHouse findCarHouseById(long id) {
        return carHouseRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }


}
