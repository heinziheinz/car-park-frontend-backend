package com.carpark.carpark.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name="_user")
public class User {

    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String name;
    private LocalDate birthdate;
    private String address;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> authorities;

    public User() {
    }

    public User(String name, LocalDate birthdate, String address, String password, Set<String> authorities) {
        this.name = name;
        this.birthdate = birthdate;
        this.address = address;
        this.password = password;
        this.authorities = authorities;
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

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(birthdate, user.birthdate) && Objects.equals(address, user.address) && Objects.equals(password, user.password) && Objects.equals(authorities, user.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthdate, address, password, authorities);
    }
}
