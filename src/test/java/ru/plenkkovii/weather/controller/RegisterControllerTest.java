package ru.plenkkovii.weather.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.plenkkovii.weather.service.SessionService;
import ru.plenkkovii.weather.service.UserService;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RegisterController.class)
public class RegisterControllerTest {
    @MockBean
    UserService userService;

    @MockBean
    private SessionService sessionService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getRegisterPage_shouldReturnRegisterPage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void postRegisterPage_shouldReturnHomePage() throws Exception {
        Mockito.when(userService.registerAndLogin(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(UUID.randomUUID());

        mockMvc.perform(post("/register")
                        .param("login", "login")
                        .param("password1", "correctPassword")
                        .param("password2", "correctPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    void postRegisterPage_withDifferentPasswords_shouldRedirectRegisterPageAndErrorMessage() throws Exception {
        mockMvc.perform(post("/register")
                        .param("login", "login")
                        .param("password1", "correctPassword")
                        .param("password2", "wrongPassword"))
                .andExpect(view().name("register"))
                .andExpect(model().attribute("passwordMismatch", "Пароли не совпадают"));
    }
}
