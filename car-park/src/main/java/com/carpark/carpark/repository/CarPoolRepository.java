package com.carpark.carpark.repository;

import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.CarHouse;
import com.carpark.carpark.model.CarPool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarPoolRepository extends JpaRepository<CarPool, Long> {
    CarPool findCarPoolById(long id);
}
