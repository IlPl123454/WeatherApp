package ru.plenkkovii.weather.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.plenkkovii.weather.service.UserService;

import java.util.UUID;


@AllArgsConstructor

@Slf4j
@Controller
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam @NotBlank String login,
                            @RequestParam @NotBlank String password,
                            HttpServletResponse resp) {

        UUID sessionUuid = userService.login(login, password);

        Cookie session = new Cookie("SESSION_UUID", sessionUuid.toString());
        session.setPath("/");
        resp.addCookie(session);

        return "redirect:/home";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("SESSION_UUID")) {
                String sessionId = cookie.getValue();
                userService.logout(sessionId);
            }
        }

        return "redirect:/index";
    }
}
