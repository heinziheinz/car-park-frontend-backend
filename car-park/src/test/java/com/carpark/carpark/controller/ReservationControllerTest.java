package com.carpark.carpark.controller;

import com.carpark.carpark.model.Car;
import com.carpark.carpark.model.Reservation;
import com.carpark.carpark.model.User;
import com.carpark.carpark.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@WebMvcTest(ReservationController.class)//the fastes Context
@AutoConfigureMockMvc(addFilters = false)//to pass all Spring Security
class ReservationControllerTest {

    @MockBean
    ReservationService reservationService;
    String url = "/reservation";

    @Autowired
    MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get(url).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(reservationService).findAllReservationEntry(ArgumentMatchers.any(Pageable.class));
    }

    @Test
    void findById() throws Exception {
        long id = 1;
        mockMvc.perform(get(url + "/id/" + id).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(reservationService).findReservationByIdEntry(id);
    }

    @Test
    void delete() throws Exception {
        long id = 1;
        mockMvc.perform(MockMvcRequestBuilders.delete(url + "/" + id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(reservationService).deleteReservationEntry(id);
    }

    @Test
    void update() throws Exception {
        long id = 1;
        Car car = new Car(1, "Honda", 200, "image");
        // Convert the Car object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String carJson = objectMapper.writeValueAsString(car);
        User user = new User("Karl", LocalDate.of(2022, 10, 22), "Hufgasse 3", "wodödü", Set.of("User"));
        // Convert the User object to JSON
        String userJson = objectMapper.writeValueAsString(user);


        String json = "{\"startDate\": \"2027-12-07\", \"endDate\": \"2027-12-07\", \"car\": " + carJson + ", \"user\": " + userJson + "}";
        Reservation reservation = new Reservation(user, LocalDate.of(2023, 11, 28), LocalDate.of(2023, 11, 30));
        reservation.setCar(car);

        mockMvc.perform(put(url + "/" + id).accept(APPLICATION_JSON).contentType(APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(reservationService).updateReservationEntry(id, reservation);
    }

    @Test
    void getAllReservedCars() throws Exception {
        long userId = 1;
        mockMvc.perform(get(url + "/get-all-reserved-cars/id/" + userId).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(reservationService).getAllReservedCars(userId);
    }

    @Test
    void getAllReservedCarsUserReservations() throws Exception {
        long userId = 1;
        mockMvc.perform(get(url + "/get-all-reserved-cars-reservations-user/id/" + userId).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        // Mockito.verify(reservationService).getAllReservations(userId, ArgumentMatchers.any(Pageable.class));
        Mockito.verify(reservationService).getAllReservations(ArgumentMatchers.eq(userId), ArgumentMatchers.any(Pageable.class));

    }
}