package com.kassing.taskTracker.service;

import com.kassing.taskTracker.DAO.DepartmentDao;
import com.kassing.taskTracker.DTO.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    DepartmentDao departmentDao;


    @Override
    public Department addDepartment(Department department) throws DepartmentDataValidationException {
        validateDepartmentData(department);
        return departmentDao.addDepartment(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentDao.getAllDepartments();
    }

    @Override
    public Department getDepartmentByID(int departmentID) {

        try {
            return departmentDao.getDepartmentByID(departmentID);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateDepartment(Department department) {
        departmentDao.updateDepartment(department);
    }

    @Override
    public void deleteDepartmentByID(int departmentID) {
        departmentDao.deleteDepartmentByID(departmentID);
    }

    //Validation Helper Functions
    public void validateDepartmentData(Department department) throws DepartmentDataValidationException {
        if(department.getDepartmentName() == null || department.getDepartmentName().trim().length() ==  0){
            throw new DepartmentDataValidationException("ERROR: Name field is required.");
        }
    }
}
