package com.kassing.taskTracker.DAO;

import com.kassing.taskTracker.DTO.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeDBTest {

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    TaskDao taskDao;

    @Autowired
    TaskPriorityDao taskPriorityDao;

    @Autowired
    TaskStatusDao taskStatusDao;

    @Autowired
    ProjectDao projectDao;

    public EmployeeDBTest() {

    }

    @BeforeEach
    public void setUpClass(){

        List<Project> projects = projectDao.getAllProjects();
        for(Project project: projects) {
            projectDao.deleteProjectByID(project.getProjectID());
        }

        List<Task> tasks = taskDao.getAllTasks();
        for(Task task : tasks) {
            taskDao.deleteTaskByID(task.getTaskID());
        }

        List<Employee> employees = employeeDao.getAllEmployees();
        for(Employee employee : employees) {
            employeeDao.deleteEmployeeByID(employee.getEmployeeID());
        }

        List<Department> departments = departmentDao.getAllDepartments();
        for(Department dept : departments) {
            departmentDao.deleteDepartmentByID(dept.getDepartmentID());
        }

        List<TaskPriority> taskPriorities = taskPriorityDao.getAllTaskPriorities();
        for(TaskPriority taskPriority : taskPriorities) {
            taskPriorityDao.deleteTaskPriorityByID(taskPriority.getTaskPriorityID());
        }

        List<TaskStatus> taskStatuses = taskStatusDao.getAllTaskStatuses();
        for(TaskStatus taskStatus : taskStatuses) {
            taskStatusDao.deleteTaskStatus(taskStatus.getTaskStatusID());
        }


    }

    @Test
    @DisplayName("Get and Add Employee")
    void testGetAndAddEmployee() {

        //Create Business Development Dept
        Department businessDevelop = new Department();
        businessDevelop.setDepartmentName("Business Development");
        businessDevelop = departmentDao.addDepartment(businessDevelop);

        List<Task> tasks = new ArrayList<>();


        //Create Employee
        Employee employee = new Employee();
        employee.setFirstName("Jane");
        employee.setLastName("Doe");
        employee.setDepartment(businessDevelop);
        employee.setTasks(tasks);
        employee = employeeDao.addEmployee(employee);

        Employee daoEmployee = employeeDao.getEmployeeByID(employee.getEmployeeID());

        assertEquals(employee, daoEmployee);
    }

    @Test
    @DisplayName("Get All Employees")
    void testGetAllEmployees() {

        //Create Business Development Dept
        Department businessDevelop = new Department();
        businessDevelop.setDepartmentName("Business Development");
        businessDevelop = departmentDao.addDepartment(businessDevelop);

        List<Task> tasks = new ArrayList<>();

        //Create Employee
        Employee employee1 = new Employee();
        employee1.setFirstName("Jane");
        employee1.setLastName("Doe");
        employee1.setDepartment(businessDevelop);
        employee1.setTasks(tasks);
        employee1 = employeeDao.addEmployee(employee1);

        //Create UX/UI Design Dept
        Department uxDesign = new Department();
        uxDesign.setDepartmentName("UX/UI Design");
        uxDesign = departmentDao.addDepartment(uxDesign);

        //Create Employee
        Employee employee2 = new Employee();
        employee2.setFirstName("Luis");
        employee2.setLastName("Groot");
        employee2.setDepartment(uxDesign);
        employee2.setTasks(tasks);
        employee2 = employeeDao.addEmployee(employee2);

        List<Employee> employeesFromDao = employeeDao.getAllEmployees();

        assertEquals(2, employeesFromDao.size(), "The DAO should contain 2 employees");
        assertTrue(employeesFromDao.contains(employee1), "The DAO should contain employee1");
        assertTrue(employeesFromDao.contains(employee2), "The DAO should contain employee2");

    }

    @Test
    @DisplayName("Update Employee")
    void testUpdateEmployee() {

        //Create Business Development Dept
        Department businessDevelop = new Department();
        businessDevelop.setDepartmentName("Business Development");
        businessDevelop = departmentDao.addDepartment(businessDevelop);

        List<Task> tasks = new ArrayList<>();

        //Create Employee
        Employee employee = new Employee();
        employee.setFirstName("Jane");
        employee.setLastName("Doe");
        employee.setDepartment(businessDevelop);
        employee.setTasks(tasks);
        employee = employeeDao.addEmployee(employee);

        Employee daoEmployee = employeeDao.getEmployeeByID(employee.getEmployeeID());
        assertEquals(employee, daoEmployee);

        //Update Employee
        employee.setFirstName("John");
        employee.setLastName("Smith");

        employeeDao.updateEmployee(employee);
        assertNotEquals(employee, daoEmployee);

        daoEmployee = employeeDao.getEmployeeByID(employee.getEmployeeID());
        assertEquals(employee, daoEmployee);
    }

    @Test
    @DisplayName("Delete Employee By ID")
    void testDeleteEmployeeByID() {

        //Create Business Development Dept
        Department businessDevelop = new Department();
        businessDevelop.setDepartmentName("Business Development");
        businessDevelop = departmentDao.addDepartment(businessDevelop);

        List<Task> tasks = new ArrayList<>();

        //Create Employee
        Employee employee = new Employee();
        employee.setFirstName("Jane");
        employee.setLastName("Doe");
        employee.setDepartment(businessDevelop);
        employee.setTasks(tasks);
        employee = employeeDao.addEmployee(employee);

        Employee daoEmployee = employeeDao.getEmployeeByID(employee.getEmployeeID());
        assertEquals(employee, daoEmployee);

        //Delete Employee
        employeeDao.deleteEmployeeByID(employee.getEmployeeID());

        //Try To Retrieve Deleted Employee
        daoEmployee = employeeDao.getEmployeeByID(employee.getEmployeeID());

        assertNull(daoEmployee);

    }

}
