package com.carpark.carpark.controller;


import com.carpark.carpark.model.CarPool;
import com.carpark.carpark.repository.CarPoolRepository;
import com.carpark.carpark.service.CarPoolService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("car-pool")
public class CarPoolController {

    private final CarPoolService carPoolService;

    public CarPoolController( CarPoolService carPoolService) {

        this.carPoolService = carPoolService;
    }

    @GetMapping
    Page<CarPool> findAll(Pageable pageable) {
        return carPoolService.findAllEntry(pageable);
    }

    @PostMapping
    CarPool save(@RequestBody CarPool carPool) {
        return carPoolService.saveCarPoolEntry(carPool);
    }


    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) {
        carPoolService.deleteCarPoolByIdEntry(id);
    }

    @PutMapping("/{id}")
    CarPool update(@PathVariable long id, @RequestBody CarPool updatedCarPool) throws RescourceNotFoundException {
        return carPoolService.updateCarPool(id,updatedCarPool);
    }
}
