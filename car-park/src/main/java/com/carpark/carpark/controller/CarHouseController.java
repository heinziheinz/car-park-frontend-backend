package com.carpark.carpark.controller;


import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.CarHouse;
import com.carpark.carpark.repository.CarHouseRepository;
import com.carpark.carpark.repository.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("carhouses")
public class CarHouseController {
    private final CarHouseRepository carHouseRepository;
    private final CarRepository carRepository;

    public CarHouseController(CarHouseRepository carHouseRepository, CarRepository carRepository) {
        this.carHouseRepository = carHouseRepository;
        this.carRepository = carRepository;
    }

    @GetMapping
    Page<CarHouse> findAll(Pageable pageable) {
        return carHouseRepository.findAll(pageable);
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
            carHouse.setHouseName(updatedCarHouse.getHouseName());
            carHouse.setCapacity(updatedCarHouse.getCapacity());
            carHouse.setAddress(updatedCarHouse.getAddress());

            return carHouseRepository.save(carHouse);
        } else {
            throw new RescourceNotFoundException();
        }
    }

    @PostMapping("/{carhouseId}/cars/{carId}")
    CarHouse addCarToCarHouse(
            @PathVariable long carhouseId,
            @PathVariable long carId
    ) throws RescourceNotFoundException {

        CarHouse carHouse = carHouseRepository.findById(carhouseId)
                .orElseThrow(RescourceNotFoundException::new);
        Car car = carRepository.findById(carId)
                .orElseThrow(RescourceNotFoundException::new);

        carHouse.getCars().add(car);
        car.setCarHouse( carHouse);
        carHouseRepository.save(carHouse);
        carRepository.save(car);

        return carHouse;
    }
}
