package ru.plenkkovii.weather.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.plenkkovii.weather.model.User;
import ru.plenkkovii.weather.service.SessionService;
import ru.plenkkovii.weather.service.UserService;

import java.util.UUID;


@AllArgsConstructor

@Slf4j
@Controller
public class LoginController {

    private final UserService userService;
    private final SessionService sessionService;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam String login,
                            @RequestParam String password,
                            Model model,
                            HttpServletResponse resp) {

        User user = userService.login(login, password);

        UUID uuid = sessionService.saveSession(user);

        Cookie sessionUuid = new Cookie("SESSION_UUID", uuid.toString());
        Cookie userName = new Cookie("USER_NAME", login);
        sessionUuid.setPath("/");
        resp.addCookie(sessionUuid);
        resp.addCookie(userName);

        return "redirect:/home";
    }
}
