package com.huella.proyectohuella.Services.Impl;

import com.huella.proyectohuella.Models.Employee;
import com.huella.proyectohuella.Models.Turno;
import com.huella.proyectohuella.Repositories.TurnoRepository;
import com.huella.proyectohuella.Services.TurnoService;
import org.springframework.stereotype.Service;

@Service
public class TurnoServiceImpl implements TurnoService {

    private final TurnoRepository turnoRepository;

    public TurnoServiceImpl(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    @Override
    public Turno findLastTurnoByEmployee(Employee employee) {
        // Implementación para buscar el último turno asignado al empleado
        // Aquí debes implementar la lógica para encontrar el último turno del empleado en el repositorio
        // Por ejemplo:
        // return turnoRepository.findFirstByEmployeeOrderByIdDesc(employee);
        // Asegúrate de adaptar esta línea según la lógica y métodos de tu repositorio
        return turnoRepository.findFirstByEmployeeOrderByFechaAsignacionDesc(employee);
    }

    @Override
    public void create(Turno turno) {
        // Implementación para crear un nuevo turno
        // Por ejemplo:
        turnoRepository.save(turno);
        // Asegúrate de adaptar esta línea según la lógica y métodos de tu repositorio
    }
}

