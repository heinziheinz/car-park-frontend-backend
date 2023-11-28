package com.carpark.carpark.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Reservation {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private  User user;
    private  LocalDate startDate;
    private LocalDate endDate;

    //TODO:  @JsonIgnore prevents infinite loop but car is not returned
    @ManyToOne
    @JsonIgnore // Add @JsonIgnore to prevent infinite loop
    private Car car; // Establishes the Many-to-One relationship with Car

    public Reservation() {

    }

    public Reservation(User user, LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
    }



    // Getter for car
    public Car getCar() {
        return car;
    }

    // Setter for car
    public void setCar(Car car) {
        this.car = car;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate start) {
        this.startDate = start;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate end) {
        this.endDate = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id && Objects.equals(user, that.user) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(car, that.car);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, startDate, endDate, car);
    }
}
