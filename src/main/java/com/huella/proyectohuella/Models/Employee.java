package com.huella.proyectohuella.Models;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;

    @Column (unique = true, name = "Cedula")
    private String cedula;

    @Column (nullable = false, name = "Nombres")
    private String nombres;

    @Column (nullable = false, name = "Apellidos")
    private String apellidos;

    @Column (nullable = false, name = "Cargo")
    private String cargo;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Turno turnos;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private User user;
    
    @Override
    public String toString() {
        return "Employee{" +
                "idEmpleado'" + idEmpleado +
                ", cedula='" + cedula + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos'" + apellidos + '\'' +
                ", cargo='" + cargo + '\'' +
                '}';
    }


}
