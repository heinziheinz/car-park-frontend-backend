package com.carpark.carpark.controller;

import com.carpark.carpark.model.Car;
import com.carpark.carpark.service.CarReservationService;
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

import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

//@SpringBootTest
@WebMvcTest(CarController.class)//the fastes Context
@AutoConfigureMockMvc(addFilters = false)//to pass all Spring Security
class CarControllerTest {
    @MockBean
    CarReservationService carReservationService;
    // we test here if the get mapping  and the url is working
    @Autowired
    MockMvc mockMvc;// the mock class that spring provides for this particular testing
    Pageable pageable = mock(Pageable.class);

    String url = "/cars";

    //the endpoint should be listening to the right method and right url
    // it should be listening to JSON translated into Java Objects
    // and if the translation into JSON is also correct
    // no full url must be provided just the API
    @Test
    //@WithMockUser(authorities = {"SCOPE_ADMIN"})
    void findAll() throws Exception {
        mockMvc.perform(get(url).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
//        Mockito.verify(carReservationService).findAllPaginated(pageable);
        Mockito.verify(carReservationService).findAllPaginated(ArgumentMatchers.any(Pageable.class));
    }

    @Test
    void findAllByType() throws Exception {
        mockMvc.perform(get(url + "/type/Honda").accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(carReservationService).findAllByTypeName("Honda");
    }

    @Test
        // @WithMockUser(authorities = {"SCOPE_ADMIN"})
    void save() throws Exception {
        Car car = new Car(1, "Honda", 200, "image");
        // String json = "{/"id/": "1", typeName:'Honda', price:'200', image: 'image'}";
        String json = "{\"id\": \"1\", \"typeName\": \"Honda\", \"price\": \"200\", \"image\": \"image\"}";
        mockMvc.perform(post(url).accept(APPLICATION_JSON).contentType(APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
        long carPollId = 1;
        verify(carReservationService).saveCarService(car, carPollId);
    }

    @Test
    void delete() throws Exception {
        long id = 1;
        mockMvc.perform(MockMvcRequestBuilders.delete(url + "/" + id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(carReservationService).deleteCar(id);
    }

    @Test
    void findById() throws Exception {
        long id = 1;
        mockMvc.perform(get(url + "/id/" + id).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(carReservationService).findCarById(id);
    }

    @Test
    void update() throws Exception {
        long id = 1;
        String json = "{\"id\": \"1\", \"typeName\": \"Honda\", \"price\": \"200\", \"image\": \"image\"}";
        Car car = new Car(1, "Honda", 200, "image");

        mockMvc.perform(put(url + "/" + id).accept(APPLICATION_JSON).contentType(APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(carReservationService).updateCar(id, car);
    }

    @Test
    void rentACar() throws Exception {
        long carId = 1;
        long userId = 1;
        LocalDate startDate = LocalDate.of(2022, 10, 22);
        LocalDate endDate = LocalDate.of(2022, 10, 22);
        mockMvc.perform(post(url + "/" + carId + "/user/" + userId + "/" + startDate + "/" + endDate).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(carReservationService).rentACar(carId, userId, startDate, endDate);
    }

    @Test
    void getAllAvailableCars() throws Exception {
        LocalDate startDate = LocalDate.of(2022, 10, 22);
        LocalDate endDate = LocalDate.of(2022, 10, 22);
        mockMvc.perform(get(url + "/" + "find-available-cars-for-rent/" + startDate + "/" + endDate).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(carReservationService).findAvailableCars(startDate, endDate);
    }

    @Test
    void getAllAvailableCarsByName() throws Exception {
        String carHouseName = "SuperCar";
        LocalDate startDate = LocalDate.of(2022, 10, 22);
        LocalDate endDate = LocalDate.of(2022, 10, 22);
        mockMvc.perform(get(url + "/" + "find-available-cars-for-rent-by-name/" + carHouseName + "/" + startDate + "/" + endDate).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(carReservationService).getAvailableCarsByCarHouseName(carHouseName, startDate, endDate);
    }

    @Test
    void getCarWitchIsInSpecificCarHouse() throws Exception {
        long carHouseId = 1;
        mockMvc.perform(get(url + "/find-all-cars-with-in-carhouse/" + carHouseId).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(carReservationService).findAllCarsInCarHouse(ArgumentMatchers.eq(carHouseId), ArgumentMatchers.any(Pageable.class));
    }

    @Test
    void getAllCArsNotAllocatedToCarHouse() throws Exception{
        mockMvc.perform(get(url + "/all-cars-not-allocated-to-a-carhouse").accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(carReservationService).findAllCArsNotAllocatedToACarHouse(ArgumentMatchers.any(Pageable.class));
    }

    @Test
    void transferCarToCarPool() throws Exception{
        long carhouseId = 1;
        long carId = 1;
        long carPoolId = 1;
        mockMvc.perform(post(url + "/"+ carhouseId + "/transfer-car-to-car-pool/" + carId).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(carReservationService).transferCarFromCarHouseToCarPool(carhouseId, carId, carPoolId);
    }

}