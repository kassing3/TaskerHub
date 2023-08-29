package com.kassing.taskTracker.DAO;

import com.kassing.taskTracker.DTO.Department;
import com.kassing.taskTracker.DTO.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DepartmentDBTest {

    @Autowired
    DepartmentDao departmentDao;

    public DepartmentDBTest() {

    }

    @BeforeEach
    public void setUpClass() {

        //Clear Any Departments
        List<Department> departments = departmentDao.getAllDepartments();
        for(Department dept : departments) {
            departmentDao.deleteDepartmentByID(dept.getDepartmentID());
        }
    }

    @Test
    @DisplayName("Get and Test Department")
    void testGetAndAddDepartment(){

        //Create Business Development Dept
        Department businessDevelop = new Department();
        businessDevelop.setDepartmentName("Business Development");
        businessDevelop = departmentDao.addDepartment(businessDevelop);

        //Pull Dept from Dao
        Department testDept = departmentDao.getDepartmentByID(businessDevelop.getDepartmentID());

        assertEquals(businessDevelop, testDept);

    }

    @Test
    @DisplayName("Get All Departments")
    void testGetAllDepartments(){

        //Create UX/UI Design Dept
        Department uxDesign = new Department();
        uxDesign.setDepartmentName("UX/UI Design");
        uxDesign = departmentDao.addDepartment(uxDesign);

        //Create Product Development Dept
        Department productDevl= new Department();
        productDevl.setDepartmentName("Product Development");
        productDevl = departmentDao.addDepartment(productDevl);

        //Create Sales & Marketing Dept
        Department salesMarketing= new Department();
        salesMarketing.setDepartmentName("Sales & Marketing");
        salesMarketing = departmentDao.addDepartment(salesMarketing);

        List<Department> deptsFromDao = departmentDao.getAllDepartments();

        assertEquals(3, deptsFromDao.size(),"The DAO should contain 3 departments.");
        assertTrue(deptsFromDao.contains(uxDesign), "The DAO should contain uxDesign");
        assertTrue(deptsFromDao.contains(productDevl), "The DAO should contain productDevl");
        assertTrue(deptsFromDao.contains(salesMarketing), "The DAO should contain salesMarketing");

    }

    @Test
    @DisplayName("Update Department")
    void updateDepartment(){

        //Create Business Development Dept
        Department businessDevelop = new Department();
        businessDevelop.setDepartmentName("Business Development");
        businessDevelop = departmentDao.addDepartment(businessDevelop);

        //Pull Dept from Dao
        Department testDept = departmentDao.getDepartmentByID(businessDevelop.getDepartmentID());

        //Confirms Depts are Equal
        assertEquals(businessDevelop, testDept);

        //Update Dept
        businessDevelop.setDepartmentName("Updated");
        departmentDao.updateDepartment(businessDevelop);

        //Confirms Depts not Equal
        assertNotEquals(businessDevelop, testDept);

        testDept =departmentDao.getDepartmentByID(businessDevelop.getDepartmentID());
        assertEquals(businessDevelop, testDept);


    }

    @Test
    @DisplayName("Delete Department By ID")
    void deleteDepartmentByID(){

        //Create Business Development Dept
        Department businessDevelop = new Department();
        businessDevelop.setDepartmentName("Business Development");
        businessDevelop = departmentDao.addDepartment(businessDevelop);

        //Pull Dept from Dao
        Department testDept = departmentDao.getDepartmentByID(businessDevelop.getDepartmentID());

        //Confirms Depts are Equal
        assertEquals(businessDevelop, testDept);

        //Delete Dept
        departmentDao.deleteDepartmentByID(businessDevelop.getDepartmentID());

        //Attempts to pull Deleted Dept
        testDept = departmentDao.getDepartmentByID(businessDevelop.getDepartmentID());
        assertNull(testDept);


    }




}
