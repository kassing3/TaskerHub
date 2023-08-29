package com.kassing.taskTracker.service;

import com.kassing.taskTracker.DTO.Employee;

import java.util.List;

public interface EmployeeService {

    Employee addEmployee(Employee employee) throws EmployeeDataValidationException;
    List<Employee> getAllEmployees();
    Employee getEmployeeByID(int employeeID);
    void updateEmployee(Employee employee);
    void deleteEmployeeByID(int employeeID);
}
