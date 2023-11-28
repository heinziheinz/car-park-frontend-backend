package com.carpark.carpark.controller;

import com.carpark.carpark.model.CarHouse;
import com.carpark.carpark.service.CarHouseService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CarHouseController.class)//the fastes Context
@AutoConfigureMockMvc(addFilters = false)//to pass all Spring Security
class CarHouseControllerTest {
    @MockBean
    CarHouseService carHouseService;

    @Autowired
    MockMvc mockMvc;

    String url = "/carhouses";

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get(url).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(carHouseService).findAllCarHousesEntry(ArgumentMatchers.any(Pageable.class));
    }

    @Test
    void findById() throws Exception {
        long id = 1;
        mockMvc.perform(get(url + "/id/" + id).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(carHouseService).findCarHouseById(id);
    }

    @Test
    void save() throws Exception {
        CarHouse carHouse = new CarHouse("HollunderRental", "Taborstrasse 2", 200);
        // String json = "{/"id/": "1", typeName:'Honda', price:'200', image: 'image'}";
        String json = "{\"houseName\": \"HollunderRental\", \"address\": \"Taborstrasse 2\", \"capacity\": 200}";
        mockMvc.perform(post(url).accept(APPLICATION_JSON).contentType(APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
        long carPollId = 1;
        verify(carHouseService).carHouseSaveEntry(carHouse);
    }

    @Test
    void delete() throws Exception {
        long id = 1;
        mockMvc.perform(MockMvcRequestBuilders.delete(url + "/" + id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(carHouseService).deleteCarHouse(id);
    }

    @Test
    void update() throws Exception {
        long id = 1;
        String json = "{\"houseName\": \"HollunderRental\", \"address\": \"Taborstrasse 2\", \"capacity\": 200}";
        CarHouse carHouse = new CarHouse("HollunderRental", "Taborstrasse 2", 200);

        mockMvc.perform(put(url + "/" + id).accept(APPLICATION_JSON).contentType(APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(carHouseService).update(id, carHouse);
    }

    @Test
    void addCarToCarHouse() throws Exception {
        long carhouseId = 1;
        long carId = 1;
        long carPoolId = 1;
        mockMvc.perform(post(url + "/" + carhouseId + "/cars/" + carId).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(carHouseService).addCarToCarHouse(carhouseId, carId, carPoolId);
    }

    @Test
    void removeCarFromCarHouse() throws Exception {
        long carhouseId = 1;
        long carId = 1;
        long carPoolId = 1;
        mockMvc.perform(post(url + "/" + carhouseId + "/remove-car/" + carId).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(carHouseService).removeCarFromCarHouse(carhouseId, carPoolId, carId);
    }

    @Test
    void getAllCarHouseNames() throws Exception {
        mockMvc.perform(get(url + "/get-carhouse-names").accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(carHouseService).getAllCarHouses();
    }
}