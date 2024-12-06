package com.huella.proyectohuella.Services;

import com.huella.proyectohuella.Models.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee create(Employee employee);
    void deleteById(Long idEmpleado);
    Employee findById(Long idEmpleado);
    Employee updateEmployee(Long idEmpleado, Employee updatedEmployee);
}
