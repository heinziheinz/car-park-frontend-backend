package com.carpark.carpark.population;

import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.User;
import com.carpark.carpark.repository.CarRepository;
import com.carpark.carpark.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
public class Populator {
    @Bean
    public ApplicationRunner initializeDatabase(UserRepository userRepository, CarRepository carRepository) {
        return args -> {
            createUsers(userRepository);
            createCars(carRepository);
        };
    }

    private void createUsers(UserRepository userRepository){
        User user = new User("Karl",  LocalDate.of(1999, 12, 1), "Daheim" );
        user = userRepository.save(user);

    }

    private void createCars(CarRepository carRepository){
        Car carOne = new Car("Skoda", 20000.00);
        List<Car> setOfCars = List.of(
                new Car("Skoda", 100.00),
                new Car("Mercedes", 150.00),
                new Car("Hyundai", 100.00),
                new Car("Volvo", 300.00),
                new Car("Rolls Royce", 400.00),
                new Car("Mazda", 200.00)
        );
        List<Car> setOfCar = carRepository.saveAll(setOfCars);
    }
}
