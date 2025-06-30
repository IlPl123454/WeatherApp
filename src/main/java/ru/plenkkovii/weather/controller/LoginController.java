package ru.plenkkovii.weather.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.plenkkovii.weather.dto.LoginDTO;
import ru.plenkkovii.weather.service.UserService;

import java.util.UUID;


@AllArgsConstructor

@Slf4j
@Controller
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginDto", new LoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@Valid @ModelAttribute("loginDto") LoginDTO loginDto,
                            BindingResult bindingResult,
                            HttpServletResponse resp,
                            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("loginDto", loginDto);
            return "index";
        }

        UUID sessionUuid = userService.login(loginDto.getLogin(), loginDto.getPassword());

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
