package ru.plenkkovii.weather.exception;

public class LocationAlreadyExistException extends RuntimeException {
    public LocationAlreadyExistException(String message) {
        super(message);
    }
}
