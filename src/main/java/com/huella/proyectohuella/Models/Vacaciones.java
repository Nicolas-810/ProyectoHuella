package com.huella.proyectohuella.Models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vacaciones")
public class Vacaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK para Vacaciones

    @ManyToOne // Relación Muchos a Uno con Empleado
    @JoinColumn(name = "employee_id") // La FK en Vacaciones
    private Employee employee; // Relación con la entidad Employee

    private LocalDate fechaInicio; // Fecha de inicio de las vacaciones
    private LocalDate fechaFin; // Fecha de fin de las vacaciones

    private String estado; // Nuevo atributo: Estado de las vacaciones (Aprobado, Pendiente, Rechazado, etc.)

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Vacaciones{" +
                "id=" + id +
                ", employee=" + employee +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", estado='" + estado + '\'' +
                '}';
    }
}