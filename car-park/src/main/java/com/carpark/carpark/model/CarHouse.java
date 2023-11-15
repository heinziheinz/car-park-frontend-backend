package com.carpark.carpark.model;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class CarHouse {
    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String houseName;
    private String address;
    private long capacity;

    @OneToMany(mappedBy = "carHouse")
    private Set<Car> cars = new HashSet<>();


    public CarHouse() {

    }

    public CarHouse( String houseName, String address, long capacity) {
        this.houseName = houseName;
        this.address = address;
        this.capacity = capacity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String typeName) {
        this.houseName = typeName;
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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        CarHouse carHouse = (CarHouse) o;
//        return id == carHouse.id && capacity == carHouse.capacity && Objects.equals(houseName, carHouse.houseName) && Objects.equals(address, carHouse.address) && Objects.equals(cars, carHouse.cars);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, houseName, address, capacity, cars);
//    }
//
//    @Override
//    public String toString() {
//        return "CarHouse{" +
//                "id=" + id +
//                ", houseName='" + houseName + '\'' +
//                ", address='" + address + '\'' +
//                ", capacity=" + capacity +
//                ", cars=" + cars +
//                '}';
//    }
}
