package ru.plenkkovii.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.plenkkovii.weather.model.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private String name;
    private User user;
    private double latitude;
    private double longitude;
}
