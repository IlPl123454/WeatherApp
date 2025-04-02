package ru.plenkkovii.weather.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.plenkkovii.weather.model.User;
import ru.plenkkovii.weather.service.UserService;

@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam String login, @RequestParam String password, Model model) {
        User user = userService.login(login, password);

        String message = "Успешный вход с именем:  " + user.getLogin();
        model.addAttribute("message", message);

        return "message";
    }
}
