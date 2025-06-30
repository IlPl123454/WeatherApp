package ru.plenkkovii.weather.controller;

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
import ru.plenkkovii.weather.model.Session;
import ru.plenkkovii.weather.service.LocationService;
import ru.plenkkovii.weather.service.OpenWeatherMapApiService;
import ru.plenkkovii.weather.service.SessionService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class LocationSearchController {

    private final OpenWeatherMapApiService weatherService;
    private final LocationService locationService;
    private final SessionService sessionService;

    @GetMapping("/city-search")
    public String searchByCityName(@RequestParam String cityname, Model model) throws IOException, InterruptedException {
        List<LocationSearchViewResponseDTO> weatherByCityName = weatherService.getLocationsByCityName(cityname);

        model.addAttribute("cityNames", weatherByCityName);

        return "city-search";
    }

    @PostMapping("/city-search")
    public String addLocation(@ModelAttribute LocationApiResponseDTO city, HttpServletRequest req) {
        Optional<Session> session = sessionService.getSessionFromCookie(req.getCookies());

        if (session.isEmpty()) {
            return "redirect:/index";
        }

        locationService.addLocationRequest(city, session.get().getId());

        return "redirect:/home";
    }
}
