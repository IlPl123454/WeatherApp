package ru.plenkkovii.weather.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.plenkkovii.weather.dto.WeatherApiResponseDTO;
import ru.plenkkovii.weather.dto.LocationViewResponseDTO;
import ru.plenkkovii.weather.mapper.WeatherApiMapper;
import ru.plenkkovii.weather.model.Location;
import ru.plenkkovii.weather.repository.LocationRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WeatherService {

    private final LocationRepository locationRepository;
    private final OpenWeatherMapApiService openWeatherMapApiService;

    public List<LocationViewResponseDTO> getWeatherByUserId(int id) throws IOException, InterruptedException {
        List<LocationViewResponseDTO> weatherResponseDTOs = new ArrayList<>();

        List<Location> locations = locationRepository.findByUserId(id);
        if (locations.isEmpty()) {
            return weatherResponseDTOs;
        }

        for (Location location : locations) {
            WeatherApiResponseDTO weatherApiResponseDTO = openWeatherMapApiService
                    .getWeatherByCityCoordinates(location.getLatitude(), location.getLongitude());

            LocationViewResponseDTO weatherResponseDTO = WeatherApiMapper.toLocationViewResponseDTO(weatherApiResponseDTO, location.getName());

            weatherResponseDTOs.add(weatherResponseDTO);
        }

        return weatherResponseDTOs;
    }
}
