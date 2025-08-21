package ru.plenkkovii.weather.archive;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;
import ru.plenkkovii.weather.client.OpenWeatherHttpClient;
import ru.plenkkovii.weather.dto.LocationSearchViewResponseDTO;
import ru.plenkkovii.weather.dto.LocationViewResponseDTO;
import ru.plenkkovii.weather.dto.WeatherApiResponseDTO;
import ru.plenkkovii.weather.mapper.WeatherApiMapper;
import ru.plenkkovii.weather.service.OpenWeatherMapApiService;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class OpenWeatherApiServiceTest {
    ObjectMapper objectMapper = new ObjectMapper();
    private WireMockServer wireMockServer;
    HttpClient httpClient;
    private OpenWeatherMapApiService openWeatherMapApiService;
    private OpenWeatherHttpClient openWeatherHttpClient;
    private String locationName = "Алматы";
    private final String getLocationUrl = "http://localhost:8081/weather";
    private final String getWeatherUrl = "http://localhost:8081/location";
    private final String openweatherApiKey = "test-key";
    private final int geocodingLimitNumber = 2;


    public OpenWeatherApiServiceTest() {
        openWeatherHttpClient = new OpenWeatherHttpClient(
                HttpClient.newHttpClient(),
                getLocationUrl,
                getWeatherUrl,
                openweatherApiKey,
                geocodingLimitNumber);

        openWeatherMapApiService = new OpenWeatherMapApiService(objectMapper, openWeatherHttpClient);
    }


    @BeforeEach
    void wireMockSetup() {
        httpClient = HttpClient.newHttpClient();
        wireMockServer = new WireMockServer(8081);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8081);
    }

    @AfterEach
    void tearDown() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test
    void getLocationsByCityNameTest() throws IOException, InterruptedException {
        String requestUri = UriComponentsBuilder.fromUriString("/location")
                .queryParam("q", locationName)
                .queryParam("limit", geocodingLimitNumber)
                .queryParam("appid", openweatherApiKey)
                .build().encode().toUriString();

        stubFor(get(urlEqualTo(requestUri)).willReturn(aResponse().withBodyFile("locations.json")));

        List<LocationSearchViewResponseDTO> locations = openWeatherMapApiService.getLocationsByCityName("Алматы");

        List<LocationSearchViewResponseDTO> locationsToAssert = new ArrayList<>();

        locationsToAssert.add(new LocationSearchViewResponseDTO(
                "Almaty",
                null,
                "KZ",
                43.2363924,
                76.9457275));

        locationsToAssert.add(new LocationSearchViewResponseDTO(
                "Алматинское",
                "Akmola Region",
                "KZ",
                51.532318,
                65.79071));

        assertEquals(locations, locationsToAssert);
    }

    @Test
    void getWeatherByCoordinates() throws IOException, InterruptedException {
        double longitude = 43.2363924;
        double latitude = 76.9457275;

        String requestUri = UriComponentsBuilder.fromUriString("/weather")
                .queryParam("lat", longitude)
                .queryParam("lon", latitude)
                .queryParam("appid", openweatherApiKey)
                .queryParam("units", "metric")
                .build().toUriString();

        stubFor(get(urlEqualTo(requestUri)).willReturn(aResponse().withBodyFile("weather.json")));

        WeatherApiResponseDTO weatherApiResponseDTO = openWeatherMapApiService.getWeatherByCityCoordinates(longitude, latitude);

        LocationViewResponseDTO location = WeatherApiMapper.toLocationViewResponseDTO(
                weatherApiResponseDTO,
                weatherApiResponseDTO.getName(),
                weatherApiResponseDTO.getCoordinates().getLatitude(),
                weatherApiResponseDTO.getCoordinates().getLongitude());

        LocationViewResponseDTO weatherToAssert = LocationViewResponseDTO.builder()
                .name("Almaty")
                .country("KZ")
                .temp(28)
                .feelsLike(27)
                .weather("scattered clouds")
                .humidity(25)
                .build();

        assertEquals(location, weatherToAssert);
    }
}
