package com.carpark.carpark.service.filtercarfromcarhouse;

import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.CarHouse;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


@Component
public class FilterCarFromCarHouse {
    public Set<Car> filterCarFromCarHouse(CarHouse carHouse, long carId){
        System.out.println("carHouse = " + carHouse);
        return carHouse.getCars().stream()
                .filter((car) -> {
                    return car.getId() != carId;
                }).collect(Collectors.toSet());
    }
}
