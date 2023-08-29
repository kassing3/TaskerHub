package com.kassing.taskTracker.DAO.Mappers;

import com.kassing.taskTracker.DTO.Department;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DepartmentMapper implements RowMapper<Department> {

    @Override
    public Department mapRow(ResultSet rs, int rowNum) throws SQLException {

        Department department = new Department();

        department.setDepartmentID(rs.getInt("departmentID"));
        department.setDepartmentName(rs.getString("departmentName"));

        return department;
    }
}
