package ru.plenkkovii.weather.exception;

public class LoginAlreadyExistException extends RuntimeException {

    public LoginAlreadyExistException(String message) {
        super(message);
    }
}
