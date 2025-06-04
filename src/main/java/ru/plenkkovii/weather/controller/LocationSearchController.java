package ru.plenkkovii.weather.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.plenkkovii.weather.dto.LocationApiResponseDTO;
import ru.plenkkovii.weather.dto.LocationSearchViewResponseDTO;
import ru.plenkkovii.weather.service.LocationService;
import ru.plenkkovii.weather.service.OpenWeatherMapApiService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Controller
public class LocationSearchController {

    private final OpenWeatherMapApiService weatherService;
    private final LocationService locationService;

    @GetMapping("/city-search")
    public String searchByCityName(@RequestParam String cityname, Model model) throws IOException, InterruptedException {
        List<LocationSearchViewResponseDTO> weatherByCityName = weatherService.getLocationsByCityName(cityname);

        model.addAttribute("cityNames", weatherByCityName);

        return "city-search";
    }

    @PostMapping("/city-search")
    public String addLocation(@ModelAttribute LocationApiResponseDTO city, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String sessionId = null;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("SESSION_UUID")) {
                sessionId = cookie.getValue();
            }
        }

        if (sessionId == null) {
            return "redirect:/index";
        }

        locationService.addLocationRequest(city, UUID.fromString(sessionId));

        return "redirect:/home";
    }
}
