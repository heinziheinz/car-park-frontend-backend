package com.carpark.carpark.service.filtercarfromcarhouse;

import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.CarHouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class FilterCarFromCarHouseTest {
    FilterCarFromCarHouse filterCarFromCarHouse = new FilterCarFromCarHouse();

    @Test
    void filterCarFromCarHouse() {
        Set<Car> cars = Set.of(new Car(1, "Toyota", 200, "Image"), new Car(2, "Ferrari", 300, "Image"), new Car(3, "Honda", 500, "Image"));
        Set<Car> expected = Set.of( new Car(2, "Ferrari", 300, "Image"), new Car(3, "Honda", 500, "Image"));
        CarHouse carHouse = new CarHouse("Heine", "Heinegasse", 200);
        carHouse.setCars(cars);
        long carId = 1;

        Set<Car> actual = filterCarFromCarHouse.filterCarFromCarHouse(carHouse, carId);
        Assertions.assertEquals(expected, actual);
    }
}