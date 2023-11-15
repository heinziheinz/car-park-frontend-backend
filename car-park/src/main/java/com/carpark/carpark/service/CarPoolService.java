package com.carpark.carpark.service;

import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.CarPool;
import com.carpark.carpark.repository.CarPoolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CarPoolService {
    private final CarPoolRepository carPoolRepository;

    public CarPoolService(CarPoolRepository carPoolRepository) {
        this.carPoolRepository = carPoolRepository;
    }

    private CarPool saveCarPool(CarPool carPool) {
        return carPoolRepository.save(carPool);
    }

    private void deleteCarPoolById(long id) {
        carPoolRepository.deleteById(id);
    }

    private CarPool findCarPoolById(long id) {
        return carPoolRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    private void setCapacityAddressAndNameOfCarPool(CarPool carPool, CarPool updatedCarPool) {
        carPool.setCapacity(updatedCarPool.getCapacity());
        carPool.setAddress(updatedCarPool.getAddress());
        carPool.setCarPoolName(updatedCarPool.getCarPoolName());
    }

    public Page<CarPool> findAllEntry(Pageable pageable) {

        return carPoolRepository.findAll(pageable);
    }

    public CarPool saveCarPoolEntry(CarPool carPool) {
        return saveCarPool(carPool);
    }

    public void deleteCarPoolByIdEntry(long id) {
        deleteCarPoolById(id);
    }

    public CarPool updateCarPool(long id, CarPool updatedCarPool) {
        CarPool carPool = carPoolRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        carPool.setCapacity(updatedCarPool.getCapacity());
        carPool.setAddress(updatedCarPool.getAddress());
        carPool.setCarPoolName(updatedCarPool.getCarPoolName());
        return saveCarPool(carPool);
    }

    //TODO:LÖSCHEN
    private void deleteCarHouseSetAndSetCarPoolCarSet(Car addedCarToCarPool, CarPool carPool) {
        addedCarToCarPool.setCarHouse(null);
        addedCarToCarPool.setCarPool(carPool);
    }

    public CarPool addCarToCarPool(long carPoolId, Car addedCarToCarPool) {
        CarPool carPool = carPoolRepository.findById(carPoolId).orElseThrow(EntityNotFoundException::new);
        Set<Car> carPoolCars = carPool.getCars();
        deleteCarHouseSetAndSetCarPoolCarSet(addedCarToCarPool, carPool);
        carPoolCars.add(addedCarToCarPool);
        carPool.setCars(carPoolCars);
        return carPoolRepository.save(carPool);
    }
}
