package ru.plenkkovii.weather.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.plenkkovii.weather.client.OpenWeatherHttpClient;
import ru.plenkkovii.weather.exception.OpenWeatherApiException;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OpenWeatherHttpClientTest {

    OpenWeatherHttpClient openWeatherHttpClient;

    @Mock
    HttpClient httpClient;

    @BeforeEach
    void setUp() {
        openWeatherHttpClient = new OpenWeatherHttpClient(
                httpClient,
                "https://openweather-base-url",
                "https://geocoding-base-url",
                "test-api-key",
                5
        );
    }

    @Test
    void getCurrentWeatherByCoordinates_when200_getWeather() throws IOException, InterruptedException {
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("{status : OK}");

        when(httpClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(response);

        String responseFromClient = openWeatherHttpClient.getCurrentWeatherByCoordinates(0,0);

        assertEquals(response.body(), responseFromClient);
    }

    @Test
    void getCurrentWeatherByCoordinates_whenNot200_throwsException() throws IOException, InterruptedException {
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(500);
        when(response.body()).thenReturn("{status : error}");

        when(httpClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(response);

        assertThrows(OpenWeatherApiException.class, () -> openWeatherHttpClient
                .getCurrentWeatherByCoordinates(0,0));
    }

    @Test
    void getLocationsByName_when200_getLocations() throws IOException, InterruptedException {
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("{status : OK}");

        when(httpClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(response);

        String responseFromClient = openWeatherHttpClient.getLocationsByName("Test");

        assertEquals(response.body(), responseFromClient);
    }

    @Test
    void getLocationsByName_whenNot200_throwsException() throws IOException, InterruptedException {
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(400);
        when(response.body()).thenReturn("{status : error}");

        when(httpClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(response);

        assertThrows(OpenWeatherApiException.class, () -> openWeatherHttpClient.getLocationsByName("Test"));
    }
}
