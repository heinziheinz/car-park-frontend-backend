package com.carpark.carpark.model;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class CarHouse {
    @Id
    @GeneratedValue
    private long id;
    private String typeName;
    private String address;
    private long capacity;

    @OneToMany(mappedBy = "carHouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Car> cars = new HashSet<>();


    public CarHouse() {

    }

    public CarHouse(long id, String typeName, String address, long capacity) {
        this.id = id;
        this.typeName = typeName;
        this.address = address;
        this.capacity = capacity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
