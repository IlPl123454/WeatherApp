package ru.plenkkovii.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.plenkkovii.weather.config.EnvConfig;
import ru.plenkkovii.weather.dto.LocationApiResponseDTO;
import ru.plenkkovii.weather.dto.WeatherApiResponseDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@AllArgsConstructor
@Service
public class OpenWeatherMapApiService {

    private final HttpClient httpClient;
    private final EnvConfig.WeatherApiConfig weatherApiConfig;
    private final ObjectMapper objectMapper;


    public WeatherApiResponseDTO getWeatherByCityCoordinates(double longitude, double latitude)
            throws IOException, InterruptedException {
        //TODO проверить порядок широты и долготы
        String requestUri = String.format("%s?lat=%s&lon=%s&appid=%s&units=metric",
                weatherApiConfig.getBaseWeatherUrl(),
                longitude,
                latitude,
                weatherApiConfig.getApiKey());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUri))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), WeatherApiResponseDTO.class);
    }

    public List<LocationApiResponseDTO> getLocationsByCityName(String cityName)
            throws IOException, InterruptedException {
        String requestUri = String.format("%s?q=%s&limit=%s&appid=%s", weatherApiConfig.getBaseGeocodingUrl(),
                cityName, weatherApiConfig.getLimit(), weatherApiConfig.getApiKey());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUri))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        LocationApiResponseDTO[] locationDTOS = objectMapper.readValue(response.body(), LocationApiResponseDTO[].class);

        return List.of(locationDTOS);
    }
}