package ru.plenkkovii.weather.exception;

public class OpenWeatherApiException extends RuntimeException {
    public OpenWeatherApiException(String message) {
        super(message);
    }
}
