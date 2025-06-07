package ru.plenkkovii.weather.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.plenkkovii.weather.exception.LoginAlreadyExistException;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, Model model) {
        log.error(ex.getMessage(), ex);

        model.addAttribute("error", "Возникла непредвиденная ошибка.");
        return "error";
    }

    @ExceptionHandler(LoginAlreadyExistException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public String handleLoginAlreadyExistException(LoginAlreadyExistException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }
}
