package com.huella.proyectohuella.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "empleadoxturno")

public class EmployeexTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmployeexTurno;

    @ManyToOne
    @JoinColumn(name = "idEmpleado", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "idTurno", nullable = false)
    private Turno turno;

    @Column (nullable = false)
    private LocalDateTime fechaAsignacion;

    public void setId(Long idEmployeexTurno) {
        this.idEmployeexTurno = idEmployeexTurno;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
    
}