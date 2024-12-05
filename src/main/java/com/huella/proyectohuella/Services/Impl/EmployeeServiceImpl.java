package com.huella.proyectohuella.Services.Impl;

import com.huella.proyectohuella.Models.Employee;
import com.huella.proyectohuella.Repositories.EmployeeRepository;
import com.huella.proyectohuella.Services.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteById(Long idEmpleado) {
        employeeRepository.deleteById(idEmpleado);
    }

    @Override
    public Employee findById(Long idEmpleado) {
        return employeeRepository.findById(idEmpleado).orElse(null);
    }

    @Override
    public Employee updateEmployee(Long idEmpleado, Employee updatedEmployee) {
        return employeeRepository.findById(idEmpleado)
                .map(employee -> {
                    updatedEmployee.setIdEmpleado(idEmpleado);
                    return employeeRepository.save(updatedEmployee);
                })
                .orElseThrow(() -> new NoSuchElementException("No se encontró el empleado con el ID: " + idEmpleado));
    }
}
