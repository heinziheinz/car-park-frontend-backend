package com.carpark.carpark.population;

import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.CarPool;
import com.carpark.carpark.model.User;
import com.carpark.carpark.repository.CarPoolRepository;
import com.carpark.carpark.repository.CarRepository;
import com.carpark.carpark.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Populator {
    @Bean
    public ApplicationRunner initializeDatabase(
            UserRepository userRepository,
            CarRepository carRepository,
            CarPoolRepository carPoolRepository
            , PasswordEncoder passwordEncoder
    ) {
        return args -> {
            CarPool carPool = cerateCarPool(carPoolRepository);
            createUsers(userRepository,passwordEncoder);
            List<Car> listCars = createCars(carRepository);
            List<Car> carlist = carRepository.findAll();
            Set<Car> setOfCars1 = new HashSet<>(listCars);
            System.out.println("setOfCars = " + setOfCars1);
            carlist.forEach((car)->{
                car.setCarPool(carPool);

            });
            carPool.setCars(setOfCars1);
            carPoolRepository.save(carPool);
            carRepository.saveAll( carlist);
            //TODO: cars don`t get set in CarPool

//            carRepository.saveAll(listCars);


        };
    }

    private CarPool cerateCarPool(CarPoolRepository carPoolRepository) {
        CarPool carPool = new CarPool("Hall E", "Heidgasse", 300);
//        return carPoolRepository.save(carPool);
        return carPool;

    }

    private void createUsers(UserRepository userRepository,  PasswordEncoder passwordEncoder) {
        for (int i = 1; i <= 15; i++) {
            String email = "user" + i + "@example.com";
            String password = passwordEncoder.encode("123"); // Assuming you have the passwordEncoder defined

            User user = new User(email, LocalDate.of(1999, 12, 1), "Daheim", password, generateAuthorities(i));
            userRepository.save(user);
        }
    }
    private static Set<String> generateAuthorities(int userNumber) {
        Set<String> authorities = new HashSet<>();
        authorities.add("USER");

        // Assign ADMIN role to every 3rd user
        if (userNumber % 3 == 0) {
            authorities.add("ADMIN");
        }

        return authorities;
    }

    private List<Car> createCars(CarRepository carRepository) {
        Car carOne = new Car("Skoda", 20000.00, "https://imgur.com/fEHghpS.jpeg");
        List<Car> setOfCars = List.of(
                new Car("Skoda", 100.00, "https://imgur.com/fEHghpS.jpeg"),
                new Car("Mercedes", 150.00, "https://imgur.com/fEHghpS.jpeg"),
                new Car("Hyundai", 100.00, "https://imgur.com/fEHghpS.jpeg"),
                new Car("Volvo", 300.00, "https://imgur.com/fEHghpS.jpeg"),
                new Car("Rolls Royce", 400.00, "https://imgur.com/fEHghpS.jpeg"),
                new Car("Mazda", 200.00,"https://imgur.com/fEHghpS.jpeg")
        );

        return carRepository.saveAll(setOfCars);
    }
}


