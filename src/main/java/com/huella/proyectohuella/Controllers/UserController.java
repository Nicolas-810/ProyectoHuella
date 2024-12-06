package com.huella.proyectohuella.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.huella.proyectohuella.Services.EmployeeService;
import com.huella.proyectohuella.Services.UserService;
import com.huella.proyectohuella.Models.Employee;
import com.huella.proyectohuella.Models.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final EmployeeService employeeService;

    // Constructor para inicializar los servicios
    public UserController(UserService userService, EmployeeService employeeService) {
        this.userService = userService;
        this.employeeService = employeeService;
    }

    // Mostrar formulario para crear un nuevo usuario
    @GetMapping("/crear")
    public String mostrarFormularioCrearUsuario(Model model) {
        model.addAttribute("user", new User());
        List<Employee> empleados = employeeService.findAll();
        System.out.println("Empleados encontrados: " + empleados.size()); 
        model.addAttribute("employees", empleados); // Agregar empleados al modelo
        model.addAttribute("accion", "/user/guardar"); // Acción del formulario
        return "inicio"; // Vista donde se muestra el formulario
    }

    // Guardar un nuevo usuario
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute User user, @RequestParam("idEmpleado") Long idEmpleado, RedirectAttributes redirectAttributes) {
        try {
            // Verificamos si el empleado es null
            Employee employee = employeeService.findById(idEmpleado);
            if (employee == null) {
                throw new IllegalArgumentException("Empleado no encontrado con ID: " + idEmpleado);
            }
            
            user.setEmployee(employee); // Asignar el empleado al usuario
            userService.create(user); // Crear el usuario
            redirectAttributes.addFlashAttribute("mensaje", "Usuario creado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear usuario: " + e.getMessage());
        }
        return "redirect:/user/EstadoUsuario"; // Redirigir a la lista de usuarios
    } // Redirigir a la lista de usuarios
    

    // Listar todos los usuarios
    @GetMapping("/EstadoUsuario")
    public String listarUsuarios(Model model) {
        List<User> usuarios = userService.findAll(); // Obtener todos los usuarios
        model.addAttribute("users", usuarios); // Agregar usuarios al modelo
        return "EstadoUsuario"; // Vista con la lista de usuarios
    }

    // Actualizar un usuario existente
    @PostMapping("/actualizar/{idUser}")
    public String actualizarUsuario(@PathVariable Long idUser, @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        try {
            User usuarioExistente = userService.findById(idUser);
            usuarioExistente.setUsername(user.getUsername());
            usuarioExistente.setPassword(user.getPassword());
            usuarioExistente.setRol(user.getRol());
            userService.update(usuarioExistente); // Actualizar el usuario
            redirectAttributes.addFlashAttribute("mensaje", "Usuario actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar usuario: " + e.getMessage());
        }
        return "redirect:/user/EstadoUsuario"; // Redirigir a la lista de usuarios
    }

    // Cambiar el estado de un usuario (activo/inactivo)
    @PostMapping("/cambiarEstado/{idUser}")
    public String cambiarEstadoUsuario(@PathVariable Long idUser, RedirectAttributes redirectAttributes) {
        try {
            User usuario = userService.findById(idUser);
            usuario.setActivo(!usuario.isActivo()); // Cambiar el estado
            userService.update(usuario); // Actualizar el usuario
            redirectAttributes.addFlashAttribute("mensaje", "Estado del usuario actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cambiar el estado del usuario: " + e.getMessage());
        }
        return "redirect:/user/EstadoUsuario"; // Redirigir a la lista de usuarios
    }

    // Cambiar la contraseña de un usuario
    @PostMapping("/cambiarContrasena/{idUser}")
    public String cambiarContrasena(@PathVariable Long idUser, @RequestParam("nuevaContrasena") String nuevaContrasena, RedirectAttributes redirectAttributes) {
        try {
            User usuario = userService.findById(idUser);
            usuario.setPassword(nuevaContrasena); // Cambiar la contraseña
            userService.update(usuario); // Actualizar el usuario
            redirectAttributes.addFlashAttribute("mensaje", "Contraseña actualizada correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cambiar la contraseña: " + e.getMessage());
        }
        return "redirect:/user/EstadoUsuario"; // Redirigir a la lista de usuarios
    }
}
