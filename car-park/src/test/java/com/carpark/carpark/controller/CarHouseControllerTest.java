package com.carpark.carpark.controller;

import com.carpark.carpark.service.CarHouseService;
import com.carpark.carpark.service.CarReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CarHouseController.class)//the fastes Context
@AutoConfigureMockMvc(addFilters = false)//to pass all Spring Security
class CarHouseControllerTest {
    @MockBean
    CarHouseService carHouseService;

    @Autowired
    MockMvc mockMvc;

    String url = "/carhouses";

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void addCarToCarHouse() {
    }

    @Test
    void removeCarFromCarHouse() {
    }

    @Test
    void getAllCarHouseNames() {
    }
}