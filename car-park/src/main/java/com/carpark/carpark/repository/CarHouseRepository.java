package com.carpark.carpark.repository;

import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.CarHouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarHouseRepository extends JpaRepository<CarHouse, Long> {
}
