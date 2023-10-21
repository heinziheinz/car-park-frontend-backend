package com.carpark.carpark.controller;


import com.carpark.carpark.model.CarPool;
import com.carpark.carpark.repository.CarPoolRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("car-pool")
public class CarPoolController {

    private final CarPoolRepository carPoolRepository;

    public CarPoolController(CarPoolRepository carPoolRepository) {
        this.carPoolRepository = carPoolRepository;
    }

    @GetMapping
    Page<CarPool> findAll(Pageable pageable) {
        return carPoolRepository.findAll(pageable);
    }

    @PostMapping
    CarPool save(@RequestBody CarPool carPool) {

        return carPoolRepository.save(carPool);
    }


    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) {
        carPoolRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    CarPool update(@PathVariable long id, @RequestBody CarPool updatedCarPool) throws RescourceNotFoundException {
        Optional<CarPool> existingCarPool = carPoolRepository.findById(id);

        if (existingCarPool.isPresent()) {
            CarPool carPool = existingCarPool.get();
            carPool.setCapacity(updatedCarPool.getCapacity());
            carPool.setAddress(updatedCarPool.getAddress());
            carPool.setCarPoolName(updatedCarPool.getCarPoolName());

            return carPoolRepository.save(carPool);
        } else {
            throw new RescourceNotFoundException();
        }
    }
}
