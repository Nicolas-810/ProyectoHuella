package com.huella.proyectohuella.Controllers;

import com.huella.proyectohuella.Models.Employee;
import com.huella.proyectohuella.Models.EmployeexTurno;
import com.huella.proyectohuella.Models.Turno;
import com.huella.proyectohuella.Services.EmployeeService;
import com.huella.proyectohuella.Services.EmployeexTurnoService;
import com.huella.proyectohuella.Services.ExportService;
import com.huella.proyectohuella.Services.TurnoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TurnoController {

    private final TurnoService turnoService;
    private final EmployeeService employeeService;
    private final ExportService exportService;
    private final EmployeexTurnoService employeexTurnoService;

    public TurnoController(TurnoService turnoService, EmployeeService employeeService, ExportService exportService, EmployeexTurnoService employeexTurnoService) {
        this.turnoService = turnoService;
        this.employeeService = employeeService;
        this.exportService = exportService;
        this.employeexTurnoService = employeexTurnoService;
    }

    @GetMapping("/crearturnos")
    public String listarEmpleados(Model model) {
    List<Employee> empleados = employeeService.findAll();
    
    // Obtenemos los últimos turnos de cada empleado
    Map<Long, String> turnosAsignados = new HashMap<>();
    
    for (Employee empleado : empleados) {
        Turno ultimoTurno = turnoService.findLastTurnoByEmployee(empleado);
        if (ultimoTurno != null) {
            turnosAsignados.put(empleado.getIdEmpleado(), ultimoTurno.getTurno());
        } else {
            turnosAsignados.put(empleado.getIdEmpleado(), "Sin asignar");
        }
    }
    
    model.addAttribute("employees", empleados);
    model.addAttribute("turnosAsignados", turnosAsignados);
    return "turnos";
    }


    @PostMapping("/asignarTurno")
    public String asignarTurno(@RequestParam("employeeId") Long employeeId, @RequestParam("turno") String turno, Model model) {
        Employee empleado = employeeService.findById(employeeId);
        if (empleado == null) {
            model.addAttribute("error", "Empleado no encontrado");
            return "turnos";
        }
    
        Turno ultimoTurno = turnoService.findLastTurnoByEmployee(empleado);
    
        // Validar que no se puede cambiar turno en menos de 2 días
        if (ultimoTurno != null && ChronoUnit.DAYS.between(ultimoTurno.getFechaAsignacion(), LocalDateTime.now()) < 2) {
            model.addAttribute("error", "No puede cambiar el turno hasta al menos dos días después de la última asignación.");
            return "turnos";
        }
    
        // Crear y asignar un nuevo turno
        Turno nuevoTurno = new Turno();
        nuevoTurno.setEmployee(empleado);
        nuevoTurno.setTurno(turno);
        nuevoTurno.setFechaAsignacion(LocalDateTime.now());  // Establecer la fecha actual
        turnoService.create(nuevoTurno);

        EmployeexTurno employeexTurno = new EmployeexTurno();
        employeexTurno.setEmployee(empleado);
        employeexTurno.setTurno(nuevoTurno);
        employeexTurno.setFechaAsignacion(LocalDateTime.now());

        employeexTurnoService.create(employeexTurno);
    
        return "redirect:/crearturnos";
    }
    

    @GetMapping("/exportarturnos")
    public void exportarTurnos(HttpServletResponse response) {
        try {
            exportService.exportarTurnos(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
}
