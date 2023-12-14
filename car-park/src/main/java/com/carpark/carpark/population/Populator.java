package com.carpark.carpark.population;

import com.carpark.carpark.model.*;
import com.carpark.carpark.repository.AuthorityRepository;
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
    private static Set<String> generateAuthorities(int userNumber) {
        Set<String> authorities = new HashSet<>();
        authorities.add("USER");

        // Assign ADMIN role to every 3rd user
        if (userNumber % 3 == 0) {
            authorities.add("ADMIN");
        }

        return authorities;
    }

    @Bean
    public ApplicationRunner initializeDatabase(
            UserRepository userRepository,
            CarRepository carRepository,
            CarPoolRepository carPoolRepository
            , PasswordEncoder passwordEncoder,
            AuthorityRepository authorityRepository
    ) {
        return args -> {
            if (userRepository.count() > 0 || carRepository.count() > 0 || carPoolRepository.count() > 1) {
                System.out.println("Database is already populated. Skipping initialization.");
                return;
            }
            System.out.println("WEITER, WEITER, WEITER");
//
            CarPool carPool = cerateCarPool(carPoolRepository);
            createUsers(userRepository, passwordEncoder);
            List<Car> listCars = createCars(carRepository);
            List<Car> carlist = carRepository.findAll();
            Set<Car> setOfCars1 = new HashSet<>(listCars);
            carlist.forEach((car) -> {
                car.setCarPool(carPool);

            });
            carPool.setCars(setOfCars1);
            carPoolRepository.save(carPool);
            carRepository.saveAll(carlist);
            //TODO: cars don`t get set in CarPool

//            carRepository.saveAll(listCars);
            createAuthorities(authorityRepository);


        };
    }

    private CarPool cerateCarPool(CarPoolRepository carPoolRepository) {
        CarPool carPool = new CarPool("Hall E", "Heidgasse", 300);
//        return carPoolRepository.save(carPool);
        return carPool;

    }

    private void createUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        for (int i = 1; i <= 15; i++) {
            String email = "user" + i + "@example.com";
            String password = passwordEncoder.encode("123"); // Assuming you have the passwordEncoder defined

            User user = new User(email, LocalDate.of(1999, 12, 1), "Daheim", password, generateAuthorities(i));
            userRepository.save(user);
        }
    }

    private List<Car> createCars(CarRepository carRepository) {
        Car carOne = new Car(0,"Skoda", 20000.00, "https://imgur.com/fEHghpS.jpeg");
        List<Car> setOfCars = List.of(
                new Car(0,"Manta", 400.00, "https://imgur.com/lp5Lvj3.jpg"),
                new Car(0,"Mercedes", 150.00, "https://imgur.com/fEHghpS.jpeg"),
                new Car(0, "Hyundai", 100.00, "https://imgur.com/fEHghpS.jpeg"),
                new Car(0,"Volvo", 300.00, "https://imgur.com/fEHghpS.jpeg"),
                new Car(0,"Rolls Royce", 400.00, "https://imgur.com/fEHghpS.jpeg"),
                new Car(0,"Mazda", 200.00, "https://imgur.com/fEHghpS.jpeg"),
                new Car(0,"Mazzi", 200.00, "https://imgur.com/fEHghpS.jpeg"),
                new Car(0,"Muzzo", 200.00, "https://imgur.com/fEHghpS.jpeg"),
                new Car(0,"Yahooni", 200.00, "https://imgur.com/fEHghpS.jpeg"),
                new Car(0,"Crawler", 200.00, "https://imgur.com/fEHghpS.jpeg"),
                new Car(0,"Honda", 200.00, "https://imgur.com/fEHghpS.jpeg"),
                new Car(0,"Ferrari", 200.00, "https://imgur.com/fEHghpS.jpeg"),
                new Car(0,"Lamborghini Extra", 200.00, "https://imgur.com/fEHghpS.jpeg"),
                new Car(0,"Lamborghini ", 200.00, "https://imgur.com/fEHghpS.jpeg"),
                new Car(0,"Lamborghini Green", 200.00, "https://imgur.com/fEHghpS.jpeg"),
                new Car(0,"Lamborghini Blue", 200.00, "https://imgur.com/fEHghpS.jpeg")
        );

        return carRepository.saveAll(setOfCars);
    }

    private void createAuthorities(AuthorityRepository authorityRepository) {
        Authority authority = new Authority(AuthorityEnum.USER);
        Authority authority2 = new Authority(AuthorityEnum.ADMIN);

        authorityRepository.save(authority );
        authorityRepository.save(authority2);


    }
}


