package ru.plenkkovii.weather.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.plenkkovii.weather.dto.WeatherApiResponseDTO;
import ru.plenkkovii.weather.dto.WeatherViewResponseDTO;
import ru.plenkkovii.weather.model.Location;
import ru.plenkkovii.weather.repository.LocationRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WeatherService {

    LocationRepository locationRepository;
    OpenWeatherMapApiService openWeatherMapApiService;

    public List<WeatherViewResponseDTO> getWeatherByUserId(int id) throws IOException, InterruptedException {
        List<WeatherViewResponseDTO> weatherResponseDTOs = new ArrayList<>();

        List<Location> locations = locationRepository.findByUserId(id);
        if (locations.isEmpty()) {
            return weatherResponseDTOs;
        }

        for (Location location : locations) {
            WeatherApiResponseDTO weatherApiResponseDTO = openWeatherMapApiService
                    .getWeatherByCityCoordinates(location.getLatitude(), location.getLongitude());

            // может тут лучше маппер использовать

            WeatherViewResponseDTO weatherResponseDTO = WeatherViewResponseDTO
                    .builder()
                    .city(location.getName())
                    .country(weatherApiResponseDTO.getSystem().getCountry())
                    .temp(weatherApiResponseDTO.getMain().getTemp())
                    .feelsLike(weatherApiResponseDTO.getMain().getFeelsLike())
                    .weather(weatherApiResponseDTO.getWeather().getFirst().getDescription())
                    .build();

            weatherResponseDTOs.add(weatherResponseDTO);
        }

        return weatherResponseDTOs;
    }
}
