package ru.plenkkovii.weather.handler;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ru.plenkkovii.weather.exception.*;

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

    @ExceptionHandler(OpenWeatherApiException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleOpenWeatherApiException(Exception ex, Model model) {
        log.error(ex.getMessage(), ex);

        model.addAttribute("error", "Возникла ошибка при работе с внешним сервисом. Попробуйте позже.");
        return "error";
    }

    @ExceptionHandler({LoginAlreadyExistException.class,
            UserNotFoundException.class,
            SessionExpiredException.class,
            WrongPasswordException.class,
            LocationAlreadyExistException.class,
            EntityNotFoundException.class
    })
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public String handleLoginAlreadyExistException(Exception ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleNoResourceFoundException(Model model) {
        model.addAttribute("error", "Ошибка 404. Не найдено.");
        return "error";
    }
}
