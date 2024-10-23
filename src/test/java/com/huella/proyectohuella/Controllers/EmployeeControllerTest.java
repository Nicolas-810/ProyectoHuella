package com.huella.proyectohuella.Controllers;

import com.huella.proyectohuella.Models.Employee;
import com.huella.proyectohuella.Services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configuramos MockMvc con un resolver de vistas si es necesario para evitar errores en las pruebas de controladores
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        // Inicializamos MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testMostrarFormularioCrearEmpleado() throws Exception {
        mockMvc.perform(get("/employee/crear"))
                .andExpect(status().isOk())
                .andExpect(view().name("crearEmpleado"))
                .andExpect(model().attributeExists("employee"));
    }

    @Test
    public void testListarEmpleados() throws Exception {
        // Simulamos el servicio que devuelve una lista de empleados
        List<Employee> empleados = Arrays.asList(new Employee(), new Employee());
        when(employeeService.findAll()).thenReturn(empleados);

        mockMvc.perform(get("/employee/lista"))
                .andExpect(status().isOk())
                .andExpect(view().name("ListaEmpleado"))
                .andExpect(model().attributeExists("employees"))
                .andExpect(model().attribute("employees", empleados));

        // Verificamos que el método del servicio fue llamado
        verify(employeeService, times(1)).findAll();
    }

    @Test
    public void testGuardarEmpleado() throws Exception {
        mockMvc.perform(post("/employee/guardar")
                .flashAttr("employee", new Employee()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employee/lista"));

        // Verificamos que el servicio haya sido llamado para guardar el empleado
        verify(employeeService, times(1)).create(any(Employee.class));
    }

    @Test
    public void testMostrarFormularioEditar() throws Exception {
        Employee employee = new Employee(); // Creamos un empleado simulado
        when(employeeService.findById(1L)).thenReturn(employee);

        mockMvc.perform(get("/employee/editar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editarEmpleado"))
                .andExpect(model().attributeExists("employee"))
                .andExpect(model().attribute("employee", employee));

        // Verificamos que el servicio fue llamado con el ID correcto
        verify(employeeService, times(1)).findById(1L);
    }

    @Test
    public void testEliminarEmpleado() throws Exception {
        mockMvc.perform(post("/employee/eliminar/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employee/lista"));

        // Verificamos que el servicio fue llamado para eliminar al empleado
        verify(employeeService, times(1)).deleteById(1L);
    }
}