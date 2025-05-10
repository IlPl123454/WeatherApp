package ru.plenkkovii.weather.mapper;

import ru.plenkkovii.weather.dto.WeatherApiResponseDTO;
import ru.plenkkovii.weather.dto.WeatherViewResponseDTO;

public class WeatherApiMapper {
    public static WeatherViewResponseDTO WeatherApiResponseDTOtoWeatherResponseDTO(WeatherApiResponseDTO dto) {
        WeatherApiResponseDTO.Weather weather = dto.getWeather().getFirst();

        return WeatherViewResponseDTO.builder()
                .city(dto.getName())
                .country(dto.getSystem().getCountry())
                .temp(dto.getMain().getTemp())
                .feelsLike(dto.getMain().getFeelsLike())
                .weather(weather.getDescription())
                .humidity(dto.getMain().getHumidity())
                .build();
    }
}
