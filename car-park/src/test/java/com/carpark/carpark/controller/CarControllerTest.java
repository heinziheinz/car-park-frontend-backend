package com.carpark.carpark.controller;

import com.carpark.carpark.service.CarReservationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CarController.class)//the fastes Context
class CarControllerTest {
    @MockBean
    CarReservationService carReservationService;
    // we test here if the get mapping  and the url is working
    @Autowired
    MockMvc mockMvc;// the mock class that spring provides for this particular testing
    Pageable pageable;

    String url ="/cars";
    //the endpoint should be listening to the right method and right url
    // it should be listening to JSON translated into Java Objects
    // and if the translation into JSON is also correct
    // no full url must be provided just the API
    @Test
    void findAll() throws Exception {
        mockMvc.perform(get(url).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(carReservationService).findAllPaginated(pageable);
    }

    @Test
    void findAllByType() {
    }

    @Test
    void save() {
    }
}