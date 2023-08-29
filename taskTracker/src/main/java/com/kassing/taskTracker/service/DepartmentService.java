package com.kassing.taskTracker.service;

import com.kassing.taskTracker.DTO.Department;

import java.util.List;

public interface DepartmentService {

    Department addDepartment(Department department) throws DepartmentDataValidationException;
    List<Department> getAllDepartments();
    Department getDepartmentByID(int departmentID);
    void updateDepartment(Department department);
    void deleteDepartmentByID(int departmentID);
}
