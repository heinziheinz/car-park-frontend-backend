package com.carpark.carpark.controller;


import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.CarHouse;
import com.carpark.carpark.repository.CarHouseRepository;
import com.carpark.carpark.repository.CarPoolRepository;
import com.carpark.carpark.repository.CarRepository;
import com.carpark.carpark.service.CarHouseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("carhouses")
public class CarHouseController {
    private final CarHouseRepository carHouseRepository;
    private final CarRepository carRepository;

    private final CarPoolRepository carPoolRepository;

    private final CarHouseService carHouseService;

    public CarHouseController(CarHouseRepository carHouseRepository, CarRepository carRepository, CarPoolRepository carPoolRepository, CarHouseService carHouseService) {
        this.carHouseRepository = carHouseRepository;
        this.carRepository = carRepository;
        this.carPoolRepository = carPoolRepository;
        this.carHouseService = carHouseService;
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
    void delete(@PathVariable long id) throws RescourceNotFoundException {
        carHouseService.deleteCarHouse(id);
    }

    @PutMapping("/{id}")
    CarHouse update(@PathVariable long id, @RequestBody CarHouse updatedCarHouse) throws RescourceNotFoundException {
        return carHouseService.update(id, updatedCarHouse);
    }


    @PostMapping("/{carhouseId}/cars/{carId}")
    CarHouse addCarToCarHouse(
            @PathVariable long carhouseId,
            @PathVariable long carId
    ) throws RescourceNotFoundException {
        long carPoolId = 1;
        return carHouseService.addCarToCarHouse(carhouseId, carId, carPoolId);
    }

    @PostMapping("/{carhouseId}/remove-car/{carId}")
    Car removeCarFromCarHouse(
            @PathVariable long carhouseId,
            @PathVariable long carId
    ) throws RescourceNotFoundException {
        long carPoolId = 1;
        return carHouseService.removeCarFromCarHouse(carhouseId, carPoolId, carId);
    }


}
