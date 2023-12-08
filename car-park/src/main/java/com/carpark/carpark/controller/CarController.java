package com.carpark.carpark.controller;


import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.DeletedCar;
import com.carpark.carpark.service.CarReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("cars")
public class CarController {

    private final CarReservationService carReservationService;


    public CarController(CarReservationService carReservationService) {
        this.carReservationService = carReservationService;

    }

    @GetMapping
    Page<Car> findAll(Pageable pageable) {

        return carReservationService.findAllPaginated(pageable);
    }

    @GetMapping("/type/{name}")
    List<Car> findAllByType(@PathVariable String name) {
        return carReservationService.findAllByTypeName(name);
    }

    @PostMapping
    Car save(@RequestBody Car car) {
        long carPoolId = 1;
        return carReservationService.saveCarService(car, carPoolId);
    }

    @DeleteMapping("/{id}")
    DeletedCar delete(@PathVariable long id) throws RescourceNotFoundException {
        return carReservationService.deleteCar(id);
    }

    @GetMapping("/id/{id}")
    Car findById(@PathVariable long id) throws RescourceNotFoundException {
        return carReservationService.findCarById(id);
    }

    //    Why do I have to do it that way with throw
    @PutMapping("/{id}")
    Car update(@PathVariable long id, @RequestBody Car updatedCar) throws RescourceNotFoundException {
        return carReservationService.updateCar(id, updatedCar);
    }

    @PostMapping("/{carId}/user/{userId}/{startDate}/{endDate}")
    Car rentACar(
            @PathVariable long carId,
            @PathVariable long userId,
            @PathVariable LocalDate startDate,
            @PathVariable LocalDate endDate
    ) throws RescourceNotFoundException {
        return carReservationService.rentACar(carId, userId, startDate, endDate);

    }

    @GetMapping("find-available-cars-for-rent/{startDate}/{endDate}")
    List<Car> getAllAvailableCars(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        return carReservationService.findAvailableCars(startDate, endDate);
    }

    @GetMapping("find-available-cars-for-rent/{carHouseId}/{startDate}/{endDate}")
    List<Car> getAllAvailableCars(
            @PathVariable Long carHouseId,
            @PathVariable LocalDate startDate,
            @PathVariable LocalDate endDate
    ) throws RescourceNotFoundException {
        return carReservationService.getAvailableCars(carHouseId, startDate, endDate);
    }

    @GetMapping("find-available-cars-for-rent-by-name/{carHouseName}/{startDate}/{endDate}")
    List<Car> getAllAvailableCarsByName(
            @PathVariable String carHouseName,
            @PathVariable LocalDate startDate,
            @PathVariable LocalDate endDate
    ) throws RescourceNotFoundException {
        return carReservationService.getAvailableCarsByCarHouseName(carHouseName, startDate, endDate);
    }

    @GetMapping("find-all-cars-with-in-carhouse/{carHouseId}")
    Page<Car> getCarWitchIsInSpecificCarHouse(
            @PathVariable long carHouseId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) throws RescourceNotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        return carReservationService.findAllCarsInCarHouse(carHouseId, pageable);
    }

    @GetMapping("all-cars-not-allocated-to-a-carhouse")
    Page<Car> getAllCArsNotAllocatedToCarHouse(Pageable pageable) {
        return carReservationService.findAllCArsNotAllocatedToACarHouse(pageable);
    }

    @PostMapping("/{carhouseId}/transfer-car-to-car-pool/{carId}")
    void transferCarToCarPool(
            @PathVariable long carhouseId,
            @PathVariable long carId
    ) {
        long carPoolId = 1;
        carReservationService.transferCarFromCarHouseToCarPool(carhouseId, carId, carPoolId);
    }
}
