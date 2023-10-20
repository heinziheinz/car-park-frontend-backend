package com.carpark.carpark.repository;

import com.carpark.carpark.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByTypeName(String typeName);

    @Query("SELECT c FROM Car c LEFT JOIN c.reservations r " +
            "WHERE (c NOT IN (SELECT r1.car FROM Reservation r1 WHERE :startDate BETWEEN r1.startDate AND r1.endDate OR :endDate BETWEEN r1.startDate AND r1.endDate) " +
            "OR r IS NULL)")
    List<Car> findAvailableCars(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}


