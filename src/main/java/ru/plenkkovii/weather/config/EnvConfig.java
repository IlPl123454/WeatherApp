package ru.plenkkovii.weather.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {
    @Bean
    public WeatherApiConfig weatherApiConfig() {
        return new WeatherApiConfig();
    }

    @Data
    public static class WeatherApiConfig {
        private final String apiKey;
        private final String baseWeatherUrl;
        private final String baseGeocodingUrl;
        private final String limit;

        public WeatherApiConfig() {
            Dotenv dotenv = Dotenv.load();
            this.apiKey = dotenv.get("OPENWEATHER_API_KEY");
            this.baseWeatherUrl = dotenv.get("OPENWEATHER_BASE_URL");
            this.baseGeocodingUrl = dotenv.get("GEOCODING_BASE_URL");
            this.limit = dotenv.get("GEOCODING_LIMIT_NUMBER");
        }
    }
}
