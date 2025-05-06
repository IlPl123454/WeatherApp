package ru.plenkkovii.weather.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherResponseDTO {
    private String city;
    private String country;  //or country code
    private double temp;
    private double feelsLike;
    private String weather;
    private int humidity;
}
