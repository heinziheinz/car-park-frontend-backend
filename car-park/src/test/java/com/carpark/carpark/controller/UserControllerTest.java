package com.carpark.carpark.controller;

import com.carpark.carpark.model.User;
import com.carpark.carpark.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(UserController.class)//the fastes Context
@AutoConfigureMockMvc(addFilters = false)//to pass all Spring Security
@Import(TestConfig.class) // Import TestConfig explicitly
class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;
    String url = "/users";

    @Autowired
    MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get(url).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userService).findAllUsersEntry(ArgumentMatchers.any(Pageable.class));
    }

    @Test
    void findAllByName() throws Exception {
        String name = "Paul";
        mockMvc.perform(get(url + "/name/" + name).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userService).findByNameEntry(name);
    }

    @Test
    void save() throws Exception {
        User user = new User("Paul", LocalDate.of(2022, 10, 12), "Wimbergergasse 2", "#dsl9324ös", Set.of("USER"));
        // String json = "{/"id/": "1", typeName:'Honda', price:'200', image: 'image'}";
        String json = "{\"name\": \"Paul\", \"birthdate\": \"2022-10-12\", \"address\": \"Wimbergergasse 2\", \"password\": \"#dsl9324ös\", \"authorities\": [\"USER\"]}";
        mockMvc.perform(post(url).accept(APPLICATION_JSON).contentType(APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(userService).saveUserEntry(user, passwordEncoder);
    }

    @Test
    void delete() throws Exception {
        long id = 1;
        mockMvc.perform(MockMvcRequestBuilders.delete(url + "/" + id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(userService).deleteUserEntry(id);
    }

    @Test
    void findById() throws Exception {
        long id = 1;
        mockMvc.perform(get(url + "/id/" + id).accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userService).findByIdEntry(id);
    }

    @Test
    void update() throws Exception {
        long id = 1;
        User user = new User("Paul", LocalDate.of(2022, 10, 12), "Wimbergergasse 2", "#dsl9324ös", Set.of("USER"));
        // String json = "{/"id/": "1", typeName:'Honda', price:'200', image: 'image'}";
        String json = "{\"name\": \"Paul\", \"birthdate\": \"2022-10-12\", \"address\": \"Wimbergergasse 2\", \"password\": \"#dsl9324ös\", \"authorities\": [\"USER\"]}";

        mockMvc.perform(put(url + "/" + id).accept(APPLICATION_JSON).contentType(APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userService).updateExistingUser(user, id);
    }

    @Test
    void customized() throws Exception {
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("testUser");

        // Perform the test using MockMvc
        mockMvc.perform(get(url + "/get-user").principal(authentication))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("hallo testUser"));
    }
}