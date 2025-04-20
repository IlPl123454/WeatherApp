package ru.plenkkovii.weather.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.plenkkovii.weather.service.UserService;

@AllArgsConstructor

@Controller
public class RegisterController {

    private final UserService userService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam @NotBlank String login,
                               @RequestParam @NotBlank String password,
                               HttpServletResponse resp) {
        //TODO добавить проверку правильно введенного второго пароля на клиенте

        Cookie sessionUuid = userService.registerAndLogin(login, password);

        resp.addCookie(sessionUuid);

        return "redirect:/home";
    }
}
