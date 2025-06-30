package ru.plenkkovii.weather.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationViewResponseDTO {
    private String name;
    private String country;
    private double latitude;
    private double longitude;
    private int temp;
    private int feelsLike;
    private String weather;
    private int humidity;
}
