package com.huella.proyectohuella.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.huella.proyectohuella.Services.UserService;
import com.huella.proyectohuella.Models.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrearUsuario(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("accion", "/user/guardar");
        return "inicio";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.create(user);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario creado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear usuario: " + e.getMessage());
        }
        return "redirect:/user/EstadoUsuario"; 
    }
    @GetMapping("/EstadoUsuario")
    public String listarUsuarios(Model model) {
        List<User> usuarios = userService.findAll();
        model.addAttribute("users", usuarios);
        return "EstadoUsuario"; 
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarUsuario(@PathVariable Long id, @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        try {
            User usuarioExistente = userService.findById(id);
            usuarioExistente.setUsername(user.getUsername());
            usuarioExistente.setPassword(user.getPassword());
            usuarioExistente.setRol(user.getRol());
            userService.update(usuarioExistente);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar usuario: " + e.getMessage());
        }
        return "redirect:/user/EstadoUsuario";
    }

    @PostMapping("/cambiarEstado/{id}")
    public String cambiarEstadoUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            User usuario = userService.findById(id);
            usuario.setActivo(!usuario.isActivo());
            userService.update(usuario);
            redirectAttributes.addFlashAttribute("mensaje", "Estado del usuario actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cambiar el estado del usuario: " + e.getMessage());
        }
        return "redirect:/user/EstadoUsuario";
    }

    @PostMapping("/cambiarContrasena/{id}")
    public String cambiarContrasena(@PathVariable Long id, @RequestParam("nuevaContrasena") String nuevaContrasena, RedirectAttributes redirectAttributes) {
        try {
            User usuario = userService.findById(id);
            usuario.setPassword(nuevaContrasena);
            userService.update(usuario);
            redirectAttributes.addFlashAttribute("mensaje", "Contraseña actualizada correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cambiar la contraseña: " + e.getMessage());
        }
        return "redirect:/user/EstadoUsuario";
    }
}
