package com.carpark.carpark.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public class CarPool {
    @Id
    @GeneratedValue
    private long id;
    private String carPoolName;
    private String address;
    private long capacity;

    @OneToMany(mappedBy = "carPool")
    private Set<Car> cars = new HashSet<>();


    public CarPool() {

    }

    public CarPool(String carPoolName, String address, long capacity) {
        this.carPoolName = carPoolName;
        this.address = address;
        this.capacity = capacity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCarPoolName() {
        return carPoolName;
    }

    public void setCarPoolName(String carReserve) {
        this.carPoolName = carReserve;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }
}
