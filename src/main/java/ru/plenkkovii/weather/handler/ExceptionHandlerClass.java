package ru.plenkkovii.weather.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ExceptionHandlerClass {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error";
        // пока таким образом обрабботаем все исключения, потом подумаю как лучше сделать
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleDuplicateLoginException(Exception ex, Model model) {
        if (ex.getMessage().contains("unique_name")) {
            model.addAttribute("error", "Пользователь с таким логином уже существует");
            return "error";
        } else {
            model.addAttribute("error", ex.getMessage());
        }

        return "error";
    }
}
