package ru.plenkkovii.weather.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ru.plenkkovii.weather.dto.LocationApiResponseDTO;
import ru.plenkkovii.weather.dto.LocationSearchViewResponseDTO;
import ru.plenkkovii.weather.dto.WeatherApiResponseDTO;
import ru.plenkkovii.weather.mapper.WeatherApiMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class OpenWeatherMapApiService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public OpenWeatherMapApiService(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @Value("${OPENWEATHER_BASE_URL}")
    private String openweatherBaseUrl;
    @Value("${GEOCODING_BASE_URL}")
    private String geocodingBaseUrl;
    @Value("${OPENWEATHER_API_KEY}")
    private String openweatherApiKey;
    @Value("${GEOCODING_LIMIT_NUMBER}")
    private int geocodingLimitNumber;


    public WeatherApiResponseDTO getWeatherByCityCoordinates(double longitude, double latitude)
            throws IOException, InterruptedException {
        String requestUri = UriComponentsBuilder.fromUriString(openweatherBaseUrl)
                .queryParam("lat", longitude)
                .queryParam("lon", latitude)
                .queryParam("appid", openweatherApiKey)
                .queryParam("units", "metric")
                .build().toUriString();


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUri))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), WeatherApiResponseDTO.class);
    }

    public List<LocationSearchViewResponseDTO> getLocationsByCityName(String cityName)
            throws IOException, InterruptedException {

        String requestUri = UriComponentsBuilder.fromUriString(geocodingBaseUrl)
                .queryParam("q", cityName)
                .queryParam("limit", geocodingLimitNumber)
                .queryParam("appid", openweatherApiKey)
                .build().encode().toUriString();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUri))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        List<LocationApiResponseDTO> locationApiResponseDTOS = objectMapper
                .readValue(response.body(), new TypeReference<>() {
                });


        return locationApiResponseDTOS.stream()
                .map(WeatherApiMapper::toLocationSearchViewResponseDTO)
                .toList();
    }
}