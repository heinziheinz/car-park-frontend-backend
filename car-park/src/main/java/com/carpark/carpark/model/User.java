package com.carpark.carpark.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name="usertable")
public class User {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private LocalDate birthdate;
    private String address;

    public User() {
    }

    public User(String name, LocalDate birthdate, String address) {
        this.name = name;
        this.birthdate = birthdate;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
