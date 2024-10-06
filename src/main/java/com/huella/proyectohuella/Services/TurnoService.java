package com.huella.proyectohuella.Services;

import com.huella.proyectohuella.Models.Turno;
import org.springframework.stereotype.Service;
import com.huella.proyectohuella.Models.Employee;

@Service
public interface TurnoService {
    Turno findLastTurnoByEmployee(Employee employee);
    void create(Turno turno);
}
