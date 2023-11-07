package com.carpark.carpark.controller;


import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.CarHouse;
import com.carpark.carpark.model.DeletedCarHouse;
import com.carpark.carpark.service.CarHouseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("carhouses")
public class CarHouseController {
    private final CarHouseService carHouseService;

    public CarHouseController(CarHouseService carHouseService) {
        this.carHouseService = carHouseService;
    }

    @GetMapping
    Page<CarHouse> findAll(Pageable pageable) {
        return carHouseService.findAllCarHousesEntry(pageable);
    }

    @GetMapping("/id/{id}")
    CarHouse findById(@PathVariable long id) throws RescourceNotFoundException {
        return carHouseService.findCarHouseById(id);
    }

    @PostMapping
    CarHouse save(@RequestBody CarHouse carHouse) {
        return carHouseService.carHouseSaveEntry(carHouse);
    }

    @DeleteMapping("/{id}")
    DeletedCarHouse delete(@PathVariable long id) throws RescourceNotFoundException {
        return carHouseService.deleteCarHouse(id);
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

    @GetMapping("get-carhouse-names")
    List<String> getAllCarHouseNames() {
//        return carHouseService
        List<CarHouse> carHouseList = carHouseService.getAllCarHouses();
        List<String> namesOfCarHouses = new ArrayList<>();
        carHouseList.forEach((carHouse -> {
            namesOfCarHouses.add(carHouse.getHouseName());

        }));

        return namesOfCarHouses;
    }


}
