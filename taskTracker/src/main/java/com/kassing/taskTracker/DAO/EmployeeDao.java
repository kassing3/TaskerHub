package com.kassing.taskTracker.DAO;

import com.kassing.taskTracker.DTO.Employee;
import com.kassing.taskTracker.DTO.Task;

import java.util.List;

public interface EmployeeDao {

    Employee getEmployeeByID(int employeeID);
    List<Employee> getAllEmployees();
    Employee addEmployee( Employee employee);
    void updateEmployee(Employee employee);
    void deleteEmployeeByID(int employeeID);


}
