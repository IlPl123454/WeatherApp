package ru.plenkkovii.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class WeatherApiResponseDTO {

    @JsonProperty("coord")
    private Coordinates coordinates;
    @JsonProperty("weather")
    private List<Weather> weather;
    @JsonProperty("base")
    private String base;
    @JsonProperty("main")
    private Main main;
    @JsonProperty("visibility")
    private int visibility;
    @JsonProperty("wind")
    private Wind wind;
    @JsonProperty("clouds")
    private Clouds clouds;
    @JsonProperty("dt")
    private int dt;
    @JsonProperty("sys")
    private System system;
    @JsonProperty("timezone")
    private int timezone;
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("cod")
    private int code;


    @Data
    public static class Coordinates {
        @JsonProperty("lon")
        private double latitude;
        @JsonProperty("lat")
        private double longitude;
    }

    @Data
    public static class Weather{
        private int id;
        private String main;
        private String description;
        private String icon;
    }

    @Data
    public static class Main{
        private double temp;
        @JsonProperty("feels_like")
        private double feelsLike;
        @JsonProperty("temp_min")
        private double tempMin;
        @JsonProperty("temp_max")
        private double tempMax;
        private int pressure;
        private int humidity;
        @JsonProperty("sea_level")
        private int seaLevel;
        @JsonProperty("grnd_level")
        private int grindLevel;
    }

    @Data
    public static class Wind{
        private double speed;
        private double deg;
    }

    @Data
    public static class Clouds {
        private double all;
    }

    @Data
    public static class System{
        private int type;
        private int id;
        private String country;
        private String sunrise;
        private String sunset;
    }

}
