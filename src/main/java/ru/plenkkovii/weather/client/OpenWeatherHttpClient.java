package ru.plenkkovii.weather.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import ru.plenkkovii.weather.exception.OpenWeatherApiException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class OpenWeatherHttpClient {
    private final HttpClient httpClient;
    private final String openweatherBaseUrl;
    private final String geocodingBaseUrl;
    private final String openweatherApiKey;
    private final int geocodingLimitNumber;

    public OpenWeatherHttpClient(HttpClient httpClient,
                                 @Value("${OPENWEATHER_BASE_URL}") String openweatherBaseUrl,
                                 @Value("${GEOCODING_BASE_URL}") String geocodingBaseUrl,
                                 @Value("${OPENWEATHER_API_KEY}") String openweatherApiKey,
                                 @Value("${GEOCODING_LIMIT_NUMBER}") int geocodingLimitNumber) {
        this.httpClient = httpClient;
        this.openweatherBaseUrl = openweatherBaseUrl;
        this.geocodingBaseUrl = geocodingBaseUrl;
        this.openweatherApiKey = openweatherApiKey;
        this.geocodingLimitNumber = geocodingLimitNumber;
    }

    public HttpResponse<String> getCurrentWeatherByCoordinates(double longitude, double latitude) throws IOException, InterruptedException {
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

        if (response.statusCode() != 200) {
            throw new OpenWeatherApiException(response.body());
        }

        return response;
    }

    public HttpResponse<String> getLocationsByName(String name) throws IOException, InterruptedException {
        String requestUri = UriComponentsBuilder.fromUriString(geocodingBaseUrl)
                .queryParam("q", name)
                .queryParam("limit", geocodingLimitNumber)
                .queryParam("appid", openweatherApiKey)
                .build().encode().toUriString();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUri))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new OpenWeatherApiException(response.body());
        }

        return response;
    }
}
