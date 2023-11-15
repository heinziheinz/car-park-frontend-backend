package com.carpark.carpark.service;

import com.carpark.carpark.model.*;
import com.carpark.carpark.repository.*;
import com.carpark.carpark.service.checkCarAvailablility.CarAvailable;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Component
public class CarReservationService {

    private final DateTimeService dateTimeService;
    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final CarPoolRepository carPoolRepository;

    private final UserRepository userRepository;

    private final CarHouseRepository carHouseRepository;

    private final List<CarAvailable> checkCarAvailabilitys;

    public CarReservationService(DateTimeService dateTimeService, ReservationRepository reservationRepository, CarRepository carRepository, CarPoolRepository carPoolRepository, UserRepository userRepository, CarHouseRepository carHouseRepository, List<CarAvailable> checkCarAvailabilitys) {

        this.dateTimeService = dateTimeService;
        this.reservationRepository = reservationRepository;
        this.carRepository = carRepository;
        this.carPoolRepository = carPoolRepository;
        this.userRepository = userRepository;
        this.carHouseRepository = carHouseRepository;
        this.checkCarAvailabilitys = checkCarAvailabilitys;
    }


    //TODO:  TESTED
    public boolean isCarAvailableDuringTimePeriod(Car car, LocalDate startDate, LocalDate endDate) {

        return car.getReservations().isEmpty() || car.getReservations().stream()
                .anyMatch(
                        reservation -> reservation.getStartDate().isAfter(dateTimeService.getCurrentDate()) &&
                                reservation.getStartDate().isAfter(endDate) || reservation.getEndDate().isBefore(startDate)
                );

    }

    private boolean isCarAvailableDuringTimePeriodSQLQuery(Car car, LocalDate startDate, LocalDate endDate) {
        int overlappingCount = carRepository.countOverlappingReservations(car, startDate, endDate);
        return overlappingCount == 0;
    }


    private void carGetsReserved(Car car, User user, LocalDate startDate, LocalDate endDate) {

        Reservation reservation = new Reservation(user, startDate, endDate);
        reservation.setCar(car); // Set the car for the reservation
        Reservation reservationInst = reservationRepository.save(reservation);
        Set<Reservation> reservations = car.getReservations();
        reservations.add(reservationInst);
        //TODO: why does this work??
    }


    private CarPool findCarPoolById(long id) {
        return carPoolRepository.findCarPoolById(1);
    }

    private void getCarPoolInSavedCar(Car car, CarPool carPool) {
        car.setCarPool(carPool);
    }

    private Set<Car> getCarsFormCarPool(CarPool carPool) {
        return carPool.getCars();
    }

    private void addCarToCarSet(Set<Car> cars, Car car) {
        cars.add(car);
    }

    private void addCarsToCarPool(CarPool carPool, Set<Car> cars) {
        carPool.setCars(cars);
    }


    private void updateCarNameAndPrice(Car car, Car updatedCar) {
        car.setTypeName(updatedCar.getTypeName());
        car.setPrice(updatedCar.getPrice());

    }

    public Car saveCarService(Car car, long id) {
        Car carSaved = carRepository.save(car);
        CarPool carPool = findCarPoolById(id);
        getCarPoolInSavedCar(carSaved, carPool);
        Set<Car> cars = getCarsFormCarPool(carPool);
        addCarToCarSet(cars, carSaved);
        addCarsToCarPool(carPool, cars);
        carPoolRepository.save(carPool);
        return carSaved;

    }


    public Car updateCar(long id, Car updatedCar) {
        Car car = carRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        updateCarNameAndPrice(car, updatedCar);
        return carRepository.save(car);

    }

    public Car rentACar(long carId, long userId, LocalDate startDate, LocalDate endDate) {
        Car car = carRepository.findById(carId).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        checkCarAvailabilitys.forEach((carAvailable -> {
            if (!carAvailable.isCarAvailable(car, startDate, endDate)) {
                throw new RuntimeException(new EntityNotFoundException());
            }
        }));
        carGetsReserved(car, user, startDate, endDate);
        return car;
    }

    private CarHouse findCarHouseByName(String carHouseName) {
        return carHouseRepository.findCarHouseByHouseName(carHouseName);
    }


    private List<Car> findAvailableCars(LocalDate startDate, LocalDate endDate, CarHouse carHouse) {
        return carRepository.findAvailableCarsPlusCarHouse(startDate, endDate, carHouse);
    }

    public List<Car> getAvailableCars(long id, LocalDate startDate, LocalDate endDate) {
        CarHouse carHouse = carHouseRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
       // return findAvailableCars(startDate, endDate, carHouse);
        return  carRepository.findAvailableCarsPlusCarHouse(startDate, endDate, carHouse);
    }

    public List<Car> getAvailableCarsByCarHouseName(String carHouseName, LocalDate startDate, LocalDate endDate) {
        CarHouse carHouse = findCarHouseByName(carHouseName);
        System.out.println("carHouse22 = " + carHouse);
        //return findAvailableCars(startDate, endDate, carHouse);
        return carRepository.findAvailableCarsPlusCarHouse(startDate, endDate, carHouse);
    }

    public Page<Car> findAllPaginated(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    public List<Car> findAllByTypeName(String name) {
        return carRepository.findAllByTypeName(name);
    }

    public Car findCarById(long id) {
        return carRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Car> findAvailableCars(LocalDate startDate, LocalDate endDate) {
        return carRepository.findAvailableCars(startDate, endDate);
    }

    private void deleteAllReservations(Set<Reservation> reservations) {
        reservationRepository.deleteAll(reservations);
    }

    public DeletedCar deleteCar(long id) {
        //Car car = findById(id);
        Car car = carRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Set<Reservation> reservations = car.getReservations();
        // deleteAllReservations(reservations);
        reservationRepository.deleteAll(reservations);
        carRepository.deleteById(id);
        return new DeletedCar(id, car.getTypeName());
    }


    public Page<Car> findAllCarsInCarHouse(long carHouseId, Pageable pageable) {
        CarHouse carHouse = carHouseRepository.findById(carHouseId)
                .orElseThrow(EntityNotFoundException::new);
        return carRepository.findAllByCarHouse(carHouse, pageable);
    }

    public Page<Car> findAllCArsNotAllocatedToACarHouse(Pageable pageable) {
        return carRepository.findAllByCarHouseIsNull(pageable);
    }

}
