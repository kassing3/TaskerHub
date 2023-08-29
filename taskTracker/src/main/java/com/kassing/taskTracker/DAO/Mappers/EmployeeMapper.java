package com.kassing.taskTracker.DAO.Mappers;

import com.kassing.taskTracker.DTO.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {

        Employee employee = new Employee();

        employee.setEmployeeID(rs.getInt("employeeID"));
        employee.setFirstName(rs.getString("firstName"));
        employee.setLastName(rs.getString("lastName"));


        return employee;
    }
}
