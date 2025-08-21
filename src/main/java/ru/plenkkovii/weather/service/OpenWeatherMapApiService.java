package ru.plenkkovii.weather.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.plenkkovii.weather.client.OpenWeatherHttpClient;
import ru.plenkkovii.weather.dto.LocationApiResponseDTO;
import ru.plenkkovii.weather.dto.LocationSearchViewResponseDTO;
import ru.plenkkovii.weather.dto.WeatherApiResponseDTO;
import ru.plenkkovii.weather.mapper.WeatherApiMapper;

import java.io.IOException;
import java.util.List;

@Service
public class OpenWeatherMapApiService {

    private final ObjectMapper objectMapper;
    private final OpenWeatherHttpClient openWeatherHttpClient;

    public OpenWeatherMapApiService(ObjectMapper objectMapper, OpenWeatherHttpClient openWeatherHttpClient) {
        this.objectMapper = objectMapper;
        this.openWeatherHttpClient = openWeatherHttpClient;
    }

    public WeatherApiResponseDTO getWeatherByCityCoordinates(double longitude, double latitude)
            throws IOException, InterruptedException {

        String response = openWeatherHttpClient.getCurrentWeatherByCoordinates(longitude, latitude);

        return objectMapper.readValue(response, WeatherApiResponseDTO.class);
    }

    public List<LocationSearchViewResponseDTO> getLocationsByCityName(String cityName)
            throws IOException, InterruptedException {

        String response = openWeatherHttpClient.getLocationsByName(cityName);

        List<LocationApiResponseDTO> locationApiResponseDTOS = objectMapper
                .readValue(response, new TypeReference<>() {
                });


        return locationApiResponseDTOS.stream()
                .map(WeatherApiMapper::toLocationSearchViewResponseDTO)
                .toList();
    }
}