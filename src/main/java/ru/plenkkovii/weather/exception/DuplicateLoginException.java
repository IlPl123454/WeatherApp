package ru.plenkkovii.weather.exception;

public class DuplicateLoginException extends RuntimeException {

    public DuplicateLoginException(String message) {
        super(message);
    }
}
