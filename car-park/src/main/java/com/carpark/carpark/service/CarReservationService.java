package com.carpark.carpark.service;

import com.carpark.carpark.controller.RescourceNotFoundException;
import com.carpark.carpark.model.*;
import com.carpark.carpark.repository.*;
import com.carpark.carpark.service.checkCarAvailablility.CarAvailable;
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

    private Car findById(long carId) throws RescourceNotFoundException {
        return carRepository.findById(carId).orElseThrow(RescourceNotFoundException::new);
    }

    public User getUser(long userId) throws RescourceNotFoundException {
        return userRepository.findById(userId).orElseThrow(RescourceNotFoundException::new);
    }

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

    private Car saveCar(Car car) {
        return carRepository.save(car);
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

    private void saveCarPool(CarPool carPool) {
        carPoolRepository.save(carPool);
    }

    private void updateCarNameAndPrice(Car car, Car updatedCar) {
        car.setTypeName(updatedCar.getTypeName());
        car.setPrice(updatedCar.getPrice());

    }

    public Car saveCarService(Car car, long id) {
        Car carSaved = saveCar(car);
        CarPool carPool = findCarPoolById(id);

        getCarPoolInSavedCar(carSaved, carPool);

        Set<Car> cars = getCarsFormCarPool(carPool);
        addCarToCarSet(cars, carSaved);
        addCarsToCarPool(carPool, cars);

        saveCarPool(carPool);

        return carSaved;

    }


    public Car updateCar(long id, Car updatedCar) throws RescourceNotFoundException {

        Car car = findById(id);

        updateCarNameAndPrice(car, updatedCar);

        return saveCar(car);

    }

    public Car rentACar(long carId, long userId, LocalDate startDate, LocalDate endDate) throws RescourceNotFoundException {

        Car car = findById(carId);
        User user = getUser(userId);

        checkCarAvailabilitys.forEach((carAvailable -> {
            if (!carAvailable.isCarAvailable(car, startDate, endDate)) {
                try {
                    throw new RescourceNotFoundException();
                } catch (RescourceNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }));

//        if (!isCarAvailableDuringTimePeriodSQLQuery(car, startDate, endDate)) {
//            throw new RescourceNotFoundException();
//        }

        carGetsReserved(car, user, startDate, endDate);

        return car;
    }

    private CarHouse findCarHouseById(long carHouseId) throws RescourceNotFoundException {
        return carHouseRepository.findById(carHouseId)
                .orElseThrow(RescourceNotFoundException::new);
    }

    private CarHouse findCarHouseByName(String carHouseName) throws RescourceNotFoundException {
        return carHouseRepository.findCarHouseByHouseName(carHouseName);
    }


    private Reservation getReservationById(long id) throws RescourceNotFoundException {
        return reservationRepository
                .findById(id).orElseThrow(RescourceNotFoundException::new);
    }

    private List<Car> findAvailableCars(LocalDate startDate, LocalDate endDate, CarHouse carHouse) {
        return carRepository.findAvailableCarsPlusCarHouse(startDate, endDate, carHouse);
    }

    public List<Car> getAvailableCars(long id, LocalDate startDate, LocalDate endDate) throws RescourceNotFoundException {
        CarHouse carHouse = findCarHouseById(id);
        return findAvailableCars(startDate, endDate, carHouse);
    }

    public List<Car> getAvailableCarsByCarHouseName(String carHouseName, LocalDate startDate, LocalDate endDate) throws RescourceNotFoundException {
        CarHouse carHouse = findCarHouseByName(carHouseName);
        System.out.println("carHouse22 = " + carHouse);
        return findAvailableCars(startDate, endDate, carHouse);
    }

    public Page<Car> findAllPaginated(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    public List<Car> findAllByTypeName(String name) {
        return carRepository.findAllByTypeName(name);
    }

    public Car findCarById(long id) throws RescourceNotFoundException {
        return findById(id);
    }

    public List<Car> findAvailableCars(LocalDate startDate, LocalDate endDate) {
        return carRepository.findAvailableCars(startDate, endDate);
    }

    private void deleteAllReservations(Set<Reservation> reservations) {
        reservationRepository.deleteAll(reservations);
    }

    private void deleteCarById(long id) {
        carRepository.deleteById(id);
    }

    public DeletedCar deleteCar(long id) throws RescourceNotFoundException {
        Car car = findById(id);
        Set<Reservation> reservations = car.getReservations();
        deleteAllReservations(reservations);
        deleteCarById(id);
        return new DeletedCar(id,car.getTypeName());
    }

}
