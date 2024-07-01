package org.example.vehicleregistration.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.vehicleregistration.model.User;
import org.example.vehicleregistration.model.exceptions.InvalidUserCredentialsException;
import org.example.vehicleregistration.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String getLoginPage(Model model) {
        model.addAttribute("bodyContent", "login");
        return "master-template";
    }

    @PostMapping
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        try {
            User user = this.authService.login(username, password);
            session.setAttribute("user", user);
            return "redirect:/";
        } catch (InvalidUserCredentialsException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "login";
        }
    }
}
