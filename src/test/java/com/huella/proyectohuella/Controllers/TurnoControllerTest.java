package com.huella.proyectohuella.Controllers;

import com.huella.proyectohuella.Models.Employee;
import com.huella.proyectohuella.Models.Turno;
import com.huella.proyectohuella.Services.EmployeeService;
import com.huella.proyectohuella.Services.ExportService;
import com.huella.proyectohuella.Services.TurnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TurnoControllerTest {

    @Mock
    private TurnoService turnoService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private ExportService exportService;

    @Mock
    private Model model;

    @InjectMocks
    private TurnoController turnoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void testAsignarTurnoSuccess() {
        // Simulación de un empleado encontrado
        Employee empleado = new Employee();
        empleado.setIdEmpleado(1L);
        when(employeeService.findById(1L)).thenReturn(empleado);

        // Simulación de que el empleado no tiene un turno reciente
        when(turnoService.findLastTurnoByEmployee(empleado)).thenReturn(null);

        // Llamar al método asignarTurno
        String viewName = turnoController.asignarTurno(1L, "TurnoA", model);

        // Verificar si se creó un nuevo turno
        verify(turnoService, times(1)).create(any(Turno.class));

        // Verificar si redirige correctamente
        assertEquals("redirect:/crearturnos", viewName);
    }

    @Test
    void testAsignarTurnoErrorEmpleadoNoEncontrado() {
        // Simulación de que no se encuentra el empleado
        when(employeeService.findById(1L)).thenReturn(null);

        // Llamar al método asignarTurno
        String viewName = turnoController.asignarTurno(1L, "TurnoA", model);

        // Verificar que se añade un mensaje de error al modelo
        verify(model).addAttribute(eq("error"), eq("Empleado no encontrado"));

        // Verificar si vuelve a la vista de turnos
        assertEquals("turnos", viewName);
    }

    @Test
    void testAsignarTurnoErrorCambioReciente() {
        // Simulación de un empleado encontrado
        Employee empleado = new Employee();
        empleado.setIdEmpleado(1L);
        when(employeeService.findById(1L)).thenReturn(empleado);

        // Simulación de que el empleado tiene un turno reciente
        Turno ultimoTurno = new Turno();
        ultimoTurno.setFechaAsignacion(LocalDateTime.now().minusDays(1)); // Hace 1 día
        when(turnoService.findLastTurnoByEmployee(empleado)).thenReturn(ultimoTurno);

        // Llamar al método asignarTurno
        String viewName = turnoController.asignarTurno(1L, "TurnoA", model);

        // Verificar que se añade un mensaje de error al modelo
        verify(model).addAttribute(eq("error"), eq("No puede cambiar el turno hasta al menos dos días después de la última asignación."));

        // Verificar si vuelve a la vista de turnos
        assertEquals("turnos", viewName);
    }
}
