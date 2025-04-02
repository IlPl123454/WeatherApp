package ru.plenkkovii.weather.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.plenkkovii.weather.model.User;
import ru.plenkkovii.weather.service.UserService;

@Controller
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String  registerUser(@RequestParam String login,
                               @RequestParam String password1,
                               @RequestParam String password2,
                               Model model) {

        User user = userService.save(login, password1, password2);

        String message = "Пользователь " + user.getLogin() +" успешно зарегестрирован";
        model.addAttribute("message", message);

        return "message";
    }
}
