package ru.plenkkovii.weather.service;

import org.springframework.stereotype.Service;
import ru.plenkkovii.weather.config.EnvConfig;
import ru.plenkkovii.weather.model.Location;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WeatherService {

    private final HttpClient httpClient;
    private final EnvConfig.WeatherApiConfig weatherApiConfig;

    public WeatherService(HttpClient httpClient, EnvConfig.WeatherApiConfig weatherApiConfig) {
        this.httpClient = httpClient;
        this.weatherApiConfig = weatherApiConfig;
    }

    public String getWeatherByCityName(String cityName) throws IOException, InterruptedException {
        String requestUri = weatherApiConfig.getBaseWeatherUrl() + "?q=" + cityName + "&appid=" + weatherApiConfig.getApiKey();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUri))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String getWeatherByCityCoordinates(String longitude, String latitude) throws IOException, InterruptedException {
        String requestUri = String.format("%s?lat=%s&lon=%s&appid=%s", weatherApiConfig.getBaseWeatherUrl(), latitude, longitude, weatherApiConfig.getApiKey());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUri))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String getCoordinatesByCityName(String cityName) throws IOException, InterruptedException {
        String requestUri = String.format("%s?q=%s&limit=%s&appid=%s", weatherApiConfig.getBaseGeocodingUrl(), cityName, weatherApiConfig.getLimit(), weatherApiConfig.getApiKey());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUri))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
