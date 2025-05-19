package ru.plenkkovii.weather.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ru.plenkkovii.weather.config.EnvConfig;
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

@AllArgsConstructor
@Service
public class OpenWeatherMapApiService {

    private final HttpClient httpClient;
    private final EnvConfig.WeatherApiConfig weatherApiConfig;
    private final ObjectMapper objectMapper;


    public WeatherApiResponseDTO getWeatherByCityCoordinates(double longitude, double latitude)
            throws IOException, InterruptedException {
        //TODO проверить порядок широты и долготы
        String requestUri = UriComponentsBuilder.fromUriString(weatherApiConfig.getBaseWeatherUrl())
                .queryParam("lat", longitude)
                .queryParam("lon", latitude)
                .queryParam("appid", weatherApiConfig.getApiKey())
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
        String requestUri = String.format("%s?q=%s&limit=%s&appid=%s", weatherApiConfig.getBaseGeocodingUrl(),
                cityName, weatherApiConfig.getLimit(), weatherApiConfig.getApiKey());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUri))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        List<LocationApiResponseDTO> locationApiResponseDTOS = objectMapper
                .readValue(response.body(), new TypeReference<>() {});



        return WeatherApiMapper.toLocationSearchResponseDTOList(locationApiResponseDTOS);
    }
}