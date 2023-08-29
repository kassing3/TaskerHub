package com.kassing.taskTracker.DAO;

import com.kassing.taskTracker.DTO.Department;

import java.util.List;

public interface DepartmentDao {

    Department getDepartmentByID(int departmentID);
    List<Department> getAllDepartments();
    Department addDepartment(Department department);
    void updateDepartment(Department department);
    void deleteDepartmentByID(int departmentID);
}
