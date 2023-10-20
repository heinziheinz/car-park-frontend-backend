package com.carpark.carpark.controller;


import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.CarHouse;
import com.carpark.carpark.repository.CarHouseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("carhouses")
public class CarHouseController {
    private final CarHouseRepository carHouseRepository;

    public CarHouseController(CarHouseRepository carHouseRepository) {
        this.carHouseRepository = carHouseRepository;
    }

    @GetMapping
    List<CarHouse> findAll(Pageable pageable) {
        return carHouseRepository.findAll();
    }

    @PostMapping
    CarHouse save(@RequestBody CarHouse carHouse) {
        return carHouseRepository.save(carHouse);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) {
        carHouseRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    CarHouse update(@PathVariable long id, @RequestBody CarHouse updatedCarHouse) throws RescourceNotFoundException {
        Optional<CarHouse> existingCarHouse = carHouseRepository.findById(id);

        if (existingCarHouse.isPresent()) {
            CarHouse carHouse = existingCarHouse.get();
            carHouse.setTypeName(updatedCarHouse.getTypeName());
            carHouse.setCapacity(updatedCarHouse.getCapacity());
            carHouse.setAddress(updatedCarHouse.getAddress());

            return carHouseRepository.save(carHouse);
        } else {
            throw new RescourceNotFoundException();
        }
    }
}
