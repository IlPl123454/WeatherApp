package ru.plenkkovii.weather.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.plenkkovii.weather.service.WeatherService;

import java.io.IOException;

@AllArgsConstructor
@RestController
public class WeatherController {

    WeatherService weatherService;

    @GetMapping("/get")
    public String getWeather() throws IOException, InterruptedException {
        String lan = "76.95";
        String lon ="43.25";
        String cityName = "Almaty";

        String weatherByCityCoordinates = weatherService.getWeatherByCityCoordinates(lan, lon);

        String coordinatesByCityName = weatherService.getCoordinatesByCityName(cityName);

        return coordinatesByCityName;
    }
}
