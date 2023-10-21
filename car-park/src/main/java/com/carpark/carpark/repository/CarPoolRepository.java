package com.carpark.carpark.repository;

import com.carpark.carpark.model.CarHouse;
import com.carpark.carpark.model.CarPool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarPoolRepository extends JpaRepository<CarPool, Long> {
}
