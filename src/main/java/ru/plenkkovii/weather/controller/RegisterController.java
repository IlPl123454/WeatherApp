package ru.plenkkovii.weather.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.plenkkovii.weather.service.UserService;

@AllArgsConstructor

@Controller
public class RegisterController {

    private final UserService userService;
    private final String template = "Пльзователь %s успешно зарегистрирован";

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String login,
                               @RequestParam String password,
                               Model model) {
        //TODO добавить проверку правильно введенного второго пароля на клиенте
        //TODO доабвить редирект сразу на страницу с погодой и сразу с входом в пользователя

        userService.save(login, password);

        String message = String.format(template, login);
        model.addAttribute("message", message);

        return "message";
    }
}
