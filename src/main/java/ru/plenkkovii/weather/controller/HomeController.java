package ru.plenkkovii.weather.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.plenkkovii.weather.dto.WeatherViewResponseDTO;
import ru.plenkkovii.weather.model.Session;
import ru.plenkkovii.weather.service.SessionService;
import ru.plenkkovii.weather.service.WeatherService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class HomeController {

    private final SessionService sessionService;
    private final WeatherService weatherService;



    @GetMapping("/home")
    public String home(HttpServletRequest req, Model model) throws IOException, InterruptedException {
        Cookie[] cookies = req.getCookies();
        String sessionId = null;

        //TODO сделать сервис для работы с куками
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("SESSION_UUID")) {
                sessionId = cookie.getValue();
            }
        }

        //TODO рабзбраться с проверкой тоже
        Optional<Session> session = sessionService.getSession(UUID.fromString(sessionId));

        List<WeatherViewResponseDTO> weathers = weatherService.getWeatherByUserId(session.get().getUser().getId());

        model.addAttribute("weathers", weathers);

        return "home";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}