package ru.plenkkovii.weather.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.plenkkovii.weather.handler.AuthorizationInterceptor;
import ru.plenkkovii.weather.model.Session;
import ru.plenkkovii.weather.model.User;
import ru.plenkkovii.weather.service.LocationService;
import ru.plenkkovii.weather.service.SessionService;
import ru.plenkkovii.weather.service.WeatherService;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(HomeController.class)
public class HomeControllerTest {
    @MockBean
    SessionService sessionService;

    @MockBean
    WeatherService weatherService;

    @MockBean
    LocationService locationService;

    @MockBean
    AuthorizationInterceptor interceptor;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getHomePage_shouldReturnHomePage() throws Exception {
        Mockito.when(interceptor.preHandle(
                        any(HttpServletRequest.class),
                        any(HttpServletResponse.class),
                        any()))
                .thenReturn(true);

        Session session = new Session(
                UUID.randomUUID(),
                new User(1, "test", "test"),
                Instant.now());

        Mockito.when(sessionService.getSessionFromCookie(any()))
                .thenReturn(Optional.of(session));

        mockMvc.perform(get("/home"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }
}
