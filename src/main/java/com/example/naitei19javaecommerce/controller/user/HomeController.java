package com.example.naitei19javaecommerce.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String Getbase() {
        return "user/static-pages/home-page";
    }
}
