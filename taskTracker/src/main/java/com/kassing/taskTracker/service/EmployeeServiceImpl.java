package com.kassing.taskTracker.service;

import com.kassing.taskTracker.DAO.EmployeeDao;
import com.kassing.taskTracker.DTO.Department;
import com.kassing.taskTracker.DTO.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements  EmployeeService{

    @Autowired
    EmployeeDao employeeDao;


    @Override
    public Employee addEmployee(Employee employee) throws EmployeeDataValidationException {

        validateEmployeeData(employee);

        return employeeDao.addEmployee(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }

    @Override
    public Employee getEmployeeByID(int employeeID) {

        try {
            return employeeDao.getEmployeeByID(employeeID);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateEmployee(Employee employee) {

        employeeDao.updateEmployee(employee);

    }

    @Override
    public void deleteEmployeeByID(int employeeID) {

        employeeDao.deleteEmployeeByID(employeeID);

    }

    //Validation Helper Functions
    public void validateEmployeeData(Employee employee) throws EmployeeDataValidationException {
        if(employee.getFirstName() == null
                || employee.getFirstName().trim().length() ==  0
                ||employee.getLastName() == null
                ||employee.getLastName().trim().length() ==0
                ||employee.getDepartment() == null){
            throw new EmployeeDataValidationException("ERROR: Names and Department Fields are required.");
        }
    }
}
