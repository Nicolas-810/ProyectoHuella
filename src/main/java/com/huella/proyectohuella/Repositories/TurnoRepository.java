package com.huella.proyectohuella.Repositories;

import com.huella.proyectohuella.Models.Employee;
import com.huella.proyectohuella.Models.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
    Turno findFirstByEmployeeOrderByFechaAsignacionDesc(Employee employee);
}
