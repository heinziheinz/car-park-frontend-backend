package com.carpark.carpark.repository;

import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.CarHouse;
import com.carpark.carpark.model.CarPool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.Set;

public interface CarHouseRepository extends JpaRepository<CarHouse, Long> {
//    CarHouse findCarHouseByHouseNameAAndCars
//    private String houseName;
//    private Set<Car> cars = new HashSet<>();
}
