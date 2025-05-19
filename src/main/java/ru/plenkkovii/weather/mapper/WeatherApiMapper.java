package ru.plenkkovii.weather.mapper;

import ru.plenkkovii.weather.dto.LocationApiResponseDTO;
import ru.plenkkovii.weather.dto.LocationSearchViewResponseDTO;
import ru.plenkkovii.weather.dto.WeatherApiResponseDTO;
import ru.plenkkovii.weather.dto.LocationViewResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class WeatherApiMapper {
    public static LocationViewResponseDTO toWeatherResponseDTO(WeatherApiResponseDTO dto) {
        WeatherApiResponseDTO.Weather weather = dto.getWeather().getFirst();

        return LocationViewResponseDTO.builder()
                .name(dto.getName())
                .country(dto.getSystem().getCountry())
                .temp(dto.getMain().getTemp())
                .feelsLike(dto.getMain().getFeelsLike())
                .weather(weather.getDescription())
                .humidity(dto.getMain().getHumidity())
                .build();
    }

    public static List<LocationSearchViewResponseDTO> toLocationSearchResponseDTOList(List<LocationApiResponseDTO> dtoList) {
        List<LocationSearchViewResponseDTO> locationSearchResponseDTOList = new ArrayList<>();

        for (LocationApiResponseDTO dto : dtoList) {
            locationSearchResponseDTOList.add(LocationSearchViewResponseDTO
                    .builder()
                    .name(dto.getName())
                    .state(dto.getState())
                    .country(dto.getCountry())
                    .latitude(dto.getLatitude())
                    .longitude(dto.getLongitude())
                    .build()
            );
        }

        return locationSearchResponseDTOList;
    }
}
