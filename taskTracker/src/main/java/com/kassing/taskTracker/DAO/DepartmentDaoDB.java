package com.kassing.taskTracker.DAO;

import com.kassing.taskTracker.DAO.Mappers.DepartmentMapper;
import com.kassing.taskTracker.DTO.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class DepartmentDaoDB implements DepartmentDao{

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public Department getDepartmentByID(int departmentID) {
        try{

            final String GET_DEPT_BY_ID = "SELECT * FROM Department where departmentID = ?;";

            Department department= jdbcTemplate.queryForObject(GET_DEPT_BY_ID, new DepartmentMapper(), departmentID);

            return department;

        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Department> getAllDepartments() {

        final String GET_ALL_DEPTS = "SELECT * FROM Department;";

        List<Department> departments = jdbcTemplate.query(GET_ALL_DEPTS, new DepartmentMapper());

        return departments;
    }

    @Override
    @Transactional
    public Department addDepartment(Department department) {

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        final String ADD_DEPT = "INSERT INTO Department(departmentName) VALUES (?);";

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    ADD_DEPT,
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, department.getDepartmentName());

            return statement;

        }, keyHolder);

        department.setDepartmentID(keyHolder.getKey().intValue());

        return department;
    }

    @Override
    public void updateDepartment(Department department) {

        final String UPDATE_DEPT = "UPDATE Department set departmentName = ? WHERE departmentID = ?;";

        jdbcTemplate.update(UPDATE_DEPT,
                department.getDepartmentName(),
                department.getDepartmentID());

    }

    @Override
    @Transactional
    public void deleteDepartmentByID(int departmentID) {

        final String UPDATE_EMPLOYEE_DEPT = "UPDATE Employee set departmentID = NULL WHERE departmentID = ?;";
        jdbcTemplate.update(UPDATE_EMPLOYEE_DEPT, departmentID);

        final String DELETE_DEPARTMENT = "DELETE FROM Department WHERE departmentID = ?;";
        jdbcTemplate.update(DELETE_DEPARTMENT, departmentID);

    }
}
