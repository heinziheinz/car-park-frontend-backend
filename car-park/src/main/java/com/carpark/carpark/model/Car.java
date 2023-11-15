package com.carpark.carpark.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
public class Car {
    @Id
    @GeneratedValue
    private long id;
    private String typeName;
    private double price;

    private String image;


//    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "car")
    private Set<Reservation> reservations = new HashSet<>();

    @ManyToOne
    @JsonIgnore // Add @JsonIgnore to prevent infinite loop
    private CarHouse carHouse; // Establishes the Many-to-One relationship with Car

    @ManyToOne
    @JsonIgnore // Add @JsonIgnore to prevent infinite loop
    private CarPool carPool; // Establishes the Many-to-One relationship with Car

    public CarPool getCarPool() {
        return carPool;
    }

    public void setCarPool(CarPool carPool) {
        this.carPool = carPool;
    }

    public CarHouse getCarHouse() {
        return carHouse;
    }

    public void setCarHouse(CarHouse carHouse) {
        this.carHouse = carHouse;
    }

    public Car() {

    }



    public Car(long id, String typeName, double price, String image) {
        this.id = id;
        this.typeName = typeName;
        this.price = price;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Car car = (Car) o;
//        return id == car.id && Double.compare(price, car.price) == 0 && Objects.equals(typeName, car.typeName) && Objects.equals(image, car.image) && Objects.equals(reservations, car.reservations) && Objects.equals(carHouse, car.carHouse) && Objects.equals(carPool, car.carPool);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, typeName, price, image, reservations, carHouse, carPool);
//    }
//
//    @Override
//    public String toString() {
//        return "Car{" +
//                "id=" + id +
//                ", typeName='" + typeName + '\'' +
//                ", price=" + price +
//                ", image='" + image + '\'' +
//                ", reservations=" + reservations +
//                ", carHouse=" + carHouse +
//                ", carPool=" + (carPool != null ? carPool.getCarPoolName() : null) +
//                '}';
//    }
}
