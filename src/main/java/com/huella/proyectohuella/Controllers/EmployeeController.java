package com.huella.proyectohuella.Controllers;

import com.huella.proyectohuella.Models.Employee;
import com.huella.proyectohuella.Services.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrearEmpleado(Model model) {
        model.addAttribute("employee", new Employee());
        return "crearEmpleado";
    }

    @PostMapping("/guardar")
    public String guardarEmpleado(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes) {
        try {
            employeeService.create(employee);
            redirectAttributes.addFlashAttribute("mensaje", "Empleado creado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear empleado: " + e.getMessage());
        }
        return "redirect:/employee/lista";
    }

    @GetMapping("/lista")
    public String listarEmpleados(Model model) {
        List<Employee> empleados = employeeService.findAll();
        model.addAttribute("employees", empleados);
        return "ListaEmpleado";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Employee empleado = employeeService.findById(id);
        if (empleado != null) {
            model.addAttribute("employee", empleado);
            return "editarEmpleado";
        } else {
            return "redirect:/employee/lista";
        }
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarEmpleado(@PathVariable Long id, @ModelAttribute Employee updatedEmployee, RedirectAttributes redirectAttributes) {
        logger.info("Llamada a actualizarEmpleado con id: {}", id);
        try {
            employeeService.updateEmployee(id, updatedEmployee);
            redirectAttributes.addFlashAttribute("mensaje", "Empleado actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar empleado: " + e.getMessage());
        }
        return "redirect:/employee/lista";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Empleado eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar empleado: " + e.getMessage());
        }
        return "redirect:/employee/lista";
    }
}
