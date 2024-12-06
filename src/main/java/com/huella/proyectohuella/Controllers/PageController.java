package com.huella.proyectohuella.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String MostrarFormulariologin() {
        return "login";
    }

    @GetMapping("/inicio")
    public String mostrarInicio() {
    return "inicio"; 
    }

    @GetMapping("/CrearEmpleado")
    public String MostrarFormularioCrearEmpleado() {
        return "CrearEmpleado";
    }

    @GetMapping("/CrearUsuario")
    public String MostrarFormularioCrearUsuario() {
        return "CrearUsuario";
    }

    @GetMapping("/Turnos")
    public String TurnosEmpleado() {
        return "Turnos";
    }

    @GetMapping("/EstadoUsuario")
    public String EliminarUsuario() {
        return "EstadoUsuario";
    }

    @GetMapping("/ListaEmpleado")
    public String ListaEmpleado() {
        return "ListaEmpleado";
    }
   
    @GetMapping("/ExportarTurnos")
    public String ExportarTurnos() {
        return "ExportarTurnos";
    }

    @GetMapping("/Vacaciones")
    public String Vacaciones() {
        return "Vacaciones";
    }
}
