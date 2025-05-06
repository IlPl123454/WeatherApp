package ru.plenkkovii.weather.controller;


import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import ru.plenkkovii.weather.repository.SessionRepository;
import ru.plenkkovii.weather.repository.UserRepository;
import ru.plenkkovii.weather.service.UserService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@Transactional
public class RegisterControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    private UserService userService;

    @Test
    void shouldRegisterUser() throws Exception {
        MvcResult result = mvc.perform(post("/register")
                        .param("login", "test")
                        .param("password", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"))
                .andExpect(cookie().exists("SESSION_UUID")).andReturn();

        Cookie session = result.getResponse().getCookie("SESSION_UUID");
        String uuid = session.getValue();

        assertTrue(userRepository.findUserByLogin("test").isPresent());
        assertTrue(sessionRepository.findById(UUID.fromString(uuid)).isPresent());
    }

    @Test
    void shouldNotRegisterUserIfLoginExist() throws Exception {
        // создаем user1
        userService.registerAndLogin("test", "123");

        // пытаемся создать его еще раз, ждем ошибку
        mvc.perform(post("/register")
                        .param("login", "test")
                        .param("password", "123"))
                .andExpect(status().is5xxServerError());
    }
}
