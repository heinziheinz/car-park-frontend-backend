package com.carpark.carpark.model;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
public class Car {
    @Id
    @GeneratedValue
    private long id;
    private String typeName;
    private double price;


//    @OneToMany(mappedBy = "car")
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations = new HashSet<>();

    public Car() {

    }

    public Car(String typeName, double price) {
        this.typeName = typeName;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Getter for reservations
    public Set<Reservation> getReservations() {
        return reservations;
    }


    // Setter for reservations
    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
}
