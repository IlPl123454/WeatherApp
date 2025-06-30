package ru.plenkkovii.weather.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.plenkkovii.weather.dto.LocationViewResponseDTO;
import ru.plenkkovii.weather.dto.LoginDTO;
import ru.plenkkovii.weather.model.Session;
import ru.plenkkovii.weather.service.LocationService;
import ru.plenkkovii.weather.service.SessionService;
import ru.plenkkovii.weather.service.WeatherService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class HomeController {

    private final SessionService sessionService;
    private final WeatherService weatherService;
    private final LocationService locationService;


    @GetMapping("/home")
    public String home(HttpServletRequest req, Model model) throws IOException, InterruptedException {
        Optional<Session> session = sessionService.getSessionFromCookie(req.getCookies());

        if (session.isEmpty()) {
            return "redirect:/index";
        }

        List<LocationViewResponseDTO> locations = weatherService.getWeatherByUserId(session.get().getUser().getId());

        model.addAttribute("locations", locations);

        return "home";
    }

    @DeleteMapping("/delete-location")
    public String deleteLocation(@RequestParam String name, @RequestParam double latitude, @RequestParam double longitude) {
        locationService.deleteLocation(name, longitude, latitude);

        return "redirect:/home";
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("loginDto", new LoginDTO());
        return "index";
    }
}