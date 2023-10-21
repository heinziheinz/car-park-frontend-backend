package com.carpark.carpark.controller;


import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.CarHouse;
import com.carpark.carpark.model.CarPool;
import com.carpark.carpark.repository.CarHouseRepository;
import com.carpark.carpark.repository.CarPoolRepository;
import com.carpark.carpark.repository.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("carhouses")
public class CarHouseController {
    private final CarHouseRepository carHouseRepository;
    private final CarRepository carRepository;

    private final CarPoolRepository carPoolRepository;

    public CarHouseController(CarHouseRepository carHouseRepository, CarRepository carRepository, CarPoolRepository carPoolRepository) {
        this.carHouseRepository = carHouseRepository;
        this.carRepository = carRepository;
        this.carPoolRepository = carPoolRepository;
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
        long poolId = 1;
        Optional<CarPool> carPool = carPoolRepository.findById(poolId);
        CarPool carPool1;
        System.out.println("carPool1 = " + carPool);
        if (carPool.isPresent()) {
            System.out.println("ERROR");
            carPool1 = carPool.get();
        }else{
            throw new RescourceNotFoundException();
        }


        carHouse.getCars().add(car);
        car.setCarHouse(carHouse);
        carHouseRepository.save(carHouse);
        carRepository.save(car);

        carPool1.getCars().remove(car);
        System.out.println("carPool1 = " + carPool1);


        return carHouse;
    }
}
