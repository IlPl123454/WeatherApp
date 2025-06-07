package ru.plenkkovii.weather.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.plenkkovii.weather.dto.RegistrationDTO;
import ru.plenkkovii.weather.service.UserService;

import java.util.UUID;

@AllArgsConstructor

@Controller
public class RegisterController {

    private final UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registrationDto", new RegistrationDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registrationDto") RegistrationDTO registrationDTO,
                               BindingResult bindingResult,
                               HttpServletResponse resp,
                               Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationDto", registrationDTO);
            return "register";
        }

        if (!registrationDTO.getPassword1().equals(registrationDTO.getPassword2())) {
            model.addAttribute("passwordMismatch", "Пароли не совпадают");
            return "register";
        }

        UUID sessionUuid = userService.registerAndLogin(registrationDTO.getLogin(), registrationDTO.getPassword1());

        Cookie session = new Cookie("SESSION_UUID", sessionUuid.toString());
        session.setPath("/");
        resp.addCookie(session);

        return "redirect:/home";
    }
}
