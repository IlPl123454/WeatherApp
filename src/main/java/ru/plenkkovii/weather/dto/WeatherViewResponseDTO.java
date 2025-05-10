package ru.plenkkovii.weather.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherViewResponseDTO {
    private String city;
    private String country;
    private double temp;
    private double feelsLike;
    private String weather;
    private int humidity;
}
