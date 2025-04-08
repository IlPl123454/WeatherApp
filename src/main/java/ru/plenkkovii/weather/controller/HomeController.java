package ru.plenkkovii.weather.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        System.out.println("Home");
        return "home";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}