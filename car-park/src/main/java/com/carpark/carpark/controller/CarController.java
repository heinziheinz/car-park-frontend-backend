package com.carpark.carpark.controller;


import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.CarHouse;
import com.carpark.carpark.model.User;
import com.carpark.carpark.repository.*;
import com.carpark.carpark.service.CarReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("cars")
public class CarController {
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final CarReservationService carReservationService;
    private final CarPoolRepository carPoolRepository;

    private final CarHouseRepository carHouseRepository;

    public CarController(CarRepository carRepository, UserRepository userRepository, ReservationRepository reservationRepository, CarReservationService carReservationService, CarPoolRepository carPoolRepository, CarHouseRepository carHouseRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.carReservationService = carReservationService;
        this.carPoolRepository = carPoolRepository;
        this.carHouseRepository = carHouseRepository;
    }

//    @GetMapping
//    List<Car> findAll() {
//        return carRepository.findAll();
//    }
    //galloo

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
    void delete(@PathVariable long id) {
        carRepository.deleteById(id);
    }

    @GetMapping("/id/{id}")
    Car findById(@PathVariable long id) throws RescourceNotFoundException {
        return carRepository.findById(id).orElseThrow(RescourceNotFoundException::new);
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
        return carReservationService.rentACar(carId, userId,startDate,endDate);

    }

    @GetMapping("find-available-cars-for-rent/{startDate}/{endDate}")
    List<Car> getAllAvailableCars(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        return carRepository.findAvailableCars(startDate, endDate);
    }

    @GetMapping("find-available-cars-for-rent/{carHouseId}/{startDate}/{endDate}")
    List<Car> getAllAvailableCars(
            @PathVariable Long carHouseId,
            @PathVariable LocalDate startDate,
            @PathVariable LocalDate endDate
    ) throws RescourceNotFoundException {
        return carReservationService.getAvailableCars(carHouseId, startDate,endDate);
    }
}
