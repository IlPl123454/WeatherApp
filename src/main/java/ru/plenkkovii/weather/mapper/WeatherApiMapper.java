package ru.plenkkovii.weather.mapper;

import ru.plenkkovii.weather.dto.LocationApiResponseDTO;
import ru.plenkkovii.weather.dto.LocationSearchViewResponseDTO;
import ru.plenkkovii.weather.dto.LocationViewResponseDTO;
import ru.plenkkovii.weather.dto.WeatherApiResponseDTO;

public class WeatherApiMapper {
    public static LocationViewResponseDTO toLocationViewResponseDTO(WeatherApiResponseDTO dto, String name, double latitude, double longitude) {
        WeatherApiResponseDTO.Weather weather = dto.getWeather().getFirst();

        return LocationViewResponseDTO.builder()
                .name(name)
                .country(dto.getSystem().getCountry())
                .latitude(latitude)
                .longitude(longitude)
                .temp((int) dto.getMain().getTemp())
                .feelsLike((int) dto.getMain().getFeelsLike())
                .weather(weather.getDescription())
                .humidity(dto.getMain().getHumidity())
                .build();
    }

    public static LocationSearchViewResponseDTO toLocationSearchViewResponseDTO(LocationApiResponseDTO dto) {
        return LocationSearchViewResponseDTO
                .builder()
                .name(dto.getName())
                .state(dto.getState())
                .country(dto.getCountry())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
    }
}
