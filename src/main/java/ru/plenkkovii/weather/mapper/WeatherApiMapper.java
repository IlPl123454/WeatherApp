package ru.plenkkovii.weather.mapper;

import ru.plenkkovii.weather.dto.LocationDTO;
import ru.plenkkovii.weather.dto.WeatherApiResponseDTO;
import ru.plenkkovii.weather.dto.WeatherResponseDTO;
import ru.plenkkovii.weather.model.Location;

public class WeatherApiMapper {
    public static WeatherResponseDTO WeatherApiResponseDTOtoWeatherResponseDTO(WeatherApiResponseDTO dto) {
        WeatherApiResponseDTO.Weather weather = dto.getWeather().getFirst();

        return WeatherResponseDTO.builder()
                .city(dto.getName())
                .country(dto.getSystem().getCountry())
                .temp(dto.getMain().getTemp())
                .feelsLike(dto.getMain().getFeelsLike())
                .weather(weather.getDescription())
                .humidity(dto.getMain().getHumidity())
                .build();
    }

    public static LocationDTO WeatherApiResponseDTOtoLocationDTO(WeatherApiResponseDTO dto) {
        return LocationDTO.builder()
                .name(dto.getName())
                .latitude(dto.getCoordinates().getLatitude())
                .longitude(dto.getCoordinates().getLongitude())
                .build();
    }
}
