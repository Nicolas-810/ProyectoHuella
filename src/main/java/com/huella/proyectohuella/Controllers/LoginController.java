package com.huella.proyectohuella.Controllers;

import com.huella.proyectohuella.Models.User;
import com.huella.proyectohuella.Services.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/")
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model) {
        boolean authenticated = loginService.authenticate(username, password);
        if (authenticated) {
            return "redirect:/inicio"; 
        } else {
            User user = loginService.findByUsername(username).orElse(null);
            if (user != null && !user.isActivo()) {
                model.addAttribute("error", "Usuario no está activo");
            } else {
                model.addAttribute("error", "Usuario no existe o contraseña incorrecta");
            }
            return "login"; 
        }
    }
}
