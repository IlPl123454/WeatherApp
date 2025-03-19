package org.plenkovii.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;


@Controller
public class MainController {

    @GetMapping("/hello")
    public String sayHello(Model model) {
        model.addAttribute("message", "Привет, Илья!");
        return "hello";
    }
}
