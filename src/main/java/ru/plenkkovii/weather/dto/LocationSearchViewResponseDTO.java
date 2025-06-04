package ru.plenkkovii.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationSearchViewResponseDTO {
    private String name;
    private String state;
    private String country;
    private double latitude;
    private double longitude;
}
