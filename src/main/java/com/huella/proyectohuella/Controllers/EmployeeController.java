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

    @GetMapping("/editar/{idEmpleado}")
    public String mostrarFormularioEditar(@PathVariable Long idEmpleado, Model model) {
        Employee empleado = employeeService.findById(idEmpleado);
        if (empleado != null) {
            model.addAttribute("employee", empleado);
            return "editarEmpleado";
        } else {
            return "redirect:/employee/lista";
        }
    }

    @PostMapping("/actualizar/{idEmpleado}")
    public String actualizarEmpleado(@PathVariable Long idEmpleado, @ModelAttribute Employee updatedEmployee, RedirectAttributes redirectAttributes) {
        logger.info("Llamada a actualizarEmpleado con idEmpleado: {}", idEmpleado);
        try {
            Employee existingEmployee = employeeService.findById(idEmpleado);
            if (existingEmployee == null) {
                redirectAttributes.addFlashAttribute("error", "Empleado no encontrado.");
                return "redirect:/employee/lista";
            }
            employeeService.updateEmployee(idEmpleado, updatedEmployee);
            redirectAttributes.addFlashAttribute("mensaje", "Empleado actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar empleado: " + e.getMessage());
        }
        return "redirect:/employee/lista";
    }

    @PostMapping("/eliminar/{idEmpleado}")
    public String eliminarEmpleado(@PathVariable Long idEmpleado, RedirectAttributes redirectAttributes) {
        try {
            employeeService.deleteById(idEmpleado);
            redirectAttributes.addFlashAttribute("mensaje", "Empleado eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar empleado: " + e.getMessage());
        }
        return "redirect:/employee/lista";
    }
}
