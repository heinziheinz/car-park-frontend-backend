package com.carpark.carpark.controller;

import com.carpark.carpark.model.CarHouse;
import com.carpark.carpark.model.CarPool;
import com.carpark.carpark.service.CarPoolService;
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


@WebMvcTest(CarPoolController.class)//the fastes Context
@AutoConfigureMockMvc(addFilters = false)//to pass all Spring Security
class CarPoolControllerTest {
    @MockBean
    CarPoolService carPoolService;

    String url = "/car-pool";

    @Autowired
    MockMvc mockMvc;


    @Test
    void findAll() throws Exception {
        mockMvc.perform(get(url).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(carPoolService).findAllEntry(ArgumentMatchers.any(Pageable.class));
    }

    @Test
    void save() throws Exception {
        CarPool carPool = new CarPool("carPool", "Supergasse 2", 200);
        // String json = "{/"id/": "1", typeName:'Honda', price:'200', image: 'image'}";
        String json = "{\"carPoolName\": \"carPool\", \"address\": \"Supergasse 2\", \"capacity\": 200}";
        mockMvc.perform(post(url).accept(APPLICATION_JSON).contentType(APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(carPoolService).saveCarPoolEntry(carPool);
    }

    @Test
    void delete() throws Exception {
        long id = 1;
        mockMvc.perform(MockMvcRequestBuilders.delete(url + "/" + id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(carPoolService).deleteCarPoolByIdEntry(id);
    }

    @Test
    void update() throws Exception {
        long id = 1;
        String json = "{\"carPoolName\": \"carPool\", \"address\": \"Supergasse 2\", \"capacity\": 200}";
        CarPool carPool = new CarPool("carPool", "Supergasse 2", 200);

        mockMvc.perform(put(url + "/" + id).accept(APPLICATION_JSON).contentType(APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(carPoolService).updateCarPool(id,carPool);
    }
}