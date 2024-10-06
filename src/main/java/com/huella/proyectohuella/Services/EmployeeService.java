package com.huella.proyectohuella.Services;

import com.huella.proyectohuella.Models.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee create(Employee employee);
    void deleteById(Long id);
    Employee findById(Long id);
    Employee updateEmployee(Long id, Employee updatedEmployee);
}
