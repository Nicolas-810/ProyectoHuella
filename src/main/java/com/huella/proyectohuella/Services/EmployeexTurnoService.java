package com.huella.proyectohuella.Services;

import org.springframework.stereotype.Service;

import com.huella.proyectohuella.Models.EmployeexTurno;
import com.huella.proyectohuella.Repositories.EmployeexTurnoRepository;

@Service
public class EmployeexTurnoService {
    
    private final EmployeexTurnoRepository employeexTurnoRepository;

    public EmployeexTurnoService (EmployeexTurnoRepository employeexTurnoRepository) {
        this.employeexTurnoRepository = employeexTurnoRepository;
    }
    
    public EmployeexTurno create (EmployeexTurno employeexTurno) {
        return employeexTurnoRepository.save(employeexTurno);
    }
}
