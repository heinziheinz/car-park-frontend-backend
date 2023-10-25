package com.carpark.carpark.service;

import com.carpark.carpark.controller.RescourceNotFoundException;
import com.carpark.carpark.model.CarPool;
import com.carpark.carpark.repository.CarPoolRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class CarPoolService {
    private final CarPoolRepository carPoolRepository;

    public CarPoolService(CarPoolRepository carPoolRepository) {
        this.carPoolRepository = carPoolRepository;
    }

    private Page<CarPool> findAll(Pageable pageable) {
        return carPoolRepository.findAll(pageable);
    }

    private CarPool saveCarPool(CarPool carPool) {
        return carPoolRepository.save(carPool);
    }

    private void deleteCarPoolById(long id) {
        carPoolRepository.deleteById(id);
    }

    private CarPool findCarPoolById(long id) throws RescourceNotFoundException {
        return carPoolRepository.findById(id).orElseThrow(RescourceNotFoundException::new);
    }

    private void setCapacityAddressAndNameOfCarPool(CarPool carPool, CarPool updatedCarPool){
        carPool.setCapacity(updatedCarPool.getCapacity());
        carPool.setAddress(updatedCarPool.getAddress());
        carPool.setCarPoolName(updatedCarPool.getCarPoolName());
    }

    public Page<CarPool> findAllEntry(Pageable pageable) {
        return findAll(pageable);
    }

    public CarPool saveCarPoolEntry(CarPool carPool) {
        return saveCarPool(carPool);
    }

    public void deleteCarPoolByIdEntry(long id) {
        deleteCarPoolById(id);
    }

    public CarPool updateCarPool(long id, CarPool updatedCarPool) throws RescourceNotFoundException {
        CarPool carPool = findCarPoolById(id);
        setCapacityAddressAndNameOfCarPool(carPool, updatedCarPool);
        return saveCarPool(carPool);
    }
}
