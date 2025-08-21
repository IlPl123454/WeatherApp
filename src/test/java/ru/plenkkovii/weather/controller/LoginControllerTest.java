package ru.plenkkovii.weather.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.plenkkovii.weather.exception.WrongPasswordException;
import ru.plenkkovii.weather.service.SessionService;
import ru.plenkkovii.weather.service.UserService;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {
    @MockBean
    private UserService userService;

    @MockBean
    private SessionService sessionService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getLoginPage_shouldReturnLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void postLoginPage_shouldReturnHomePage() throws Exception {
        Mockito.when(userService.login("login", "correctPassword")).thenReturn(UUID.randomUUID());

        mockMvc.perform(post("/login")
                        .param("login", "login")
                        .param("password", "correctPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    void postLoginPage_shouldRedirectErrorPage() throws Exception {
        Mockito.when(userService.login("login", "wrongPassword"))
                .thenThrow(new WrongPasswordException("Вы ввели неверный логин или пароль"));

        mockMvc.perform(post("/login")
                        .param("login", "login")
                        .param("password", "wrongPassword"))
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("error"));
    }

    @Test
    void postLoginPage_shouldShowMistakesFromValidator() throws Exception {
        mockMvc.perform(post("/login")
                        .param("login", "l")
                        .param("password", "p"))
                .andExpect(view().name("index"))
                .andExpect(model().attributeHasFieldErrors("loginDto", "login", "password"));
    }
}
