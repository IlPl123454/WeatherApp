package ru.plenkkovii.weather.unit;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import ru.plenkkovii.weather.client.OpenWeatherHttpClient;

import java.net.http.HttpClient;

public class OpenWeatherHttpClientTest {

    OpenWeatherHttpClient openWeatherHttpClient;

    @Mock
    HttpClient httpClient;

    @BeforeEach
    void setUp() {
        openWeatherHttpClient = new OpenWeatherHttpClient(
                httpClient,
                "openweather-base-url",
                "geocoding-base-url",
                "test-api-key",
                5
        );
    }

//    @Test
//    void getLocation() throws IOException {
//        when(httpClient.
//
//    }


}
