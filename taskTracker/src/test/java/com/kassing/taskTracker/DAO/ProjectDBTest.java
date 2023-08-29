package com.kassing.taskTracker.DAO;

import com.kassing.taskTracker.DTO.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjectDBTest {

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

    public ProjectDBTest() {

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

        List<Department> departments = departmentDao.getAllDepartments();
        for(Department dept : departments) {
            departmentDao.deleteDepartmentByID(dept.getDepartmentID());
        }

        List<Employee> employees = employeeDao.getAllEmployees();
        for(Employee employee : employees) {
            employeeDao.deleteEmployeeByID(employee.getEmployeeID());
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
    @DisplayName("Get and Add Project")
    void testGetAndAddProject() {

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

        //Create Project
        Project project = new Project();
        project.setProjectName("Launch Task Tracker Product");
        project.setProjectDesc("The purpose of this project is to launch a productivity tool to help with project management");
        project.setEmployee(employee);
        project.setTasks(tasks);
        project = projectDao.addProject(project);


        Project projectFromDao = projectDao.getProjectByID(project.getProjectID());

        assertEquals(project, projectFromDao);

    }

    @Test
    @DisplayName("Get All Projects")
    void testGetAllProjects(){

        ///Create Business Development Dept
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

        //Create Project
        Project project = new Project();
        project.setProjectName("Launch Task Tracker Product");
        project.setProjectDesc("The purpose of this project is to launch a productivity tool to help with project management");
        project.setEmployee(employee);
        project.setTasks(tasks);
        project = projectDao.addProject(project);

        ///Create UX/UI Design Dept
        Department design = new Department();
        design.setDepartmentName("UX/UI Design");
        design = departmentDao.addDepartment(businessDevelop);

        List<Task> tasks2 = new ArrayList<>();

        //Create Employee2
        Employee employee2 = new Employee();
        employee2.setFirstName("Hobbs");
        employee2.setLastName("Shaw");
        employee2.setDepartment(design);
        employee2.setTasks(tasks);
        employee2 = employeeDao.addEmployee(employee2);

        //Create Project2
        Project project2 = new Project();
        project2.setProjectName("Project 2");
        project2.setProjectDesc("The purpose of this project is to launch a productivity tool to help with project management");
        project2.setEmployee(employee2);
        project2.setTasks(tasks2);
        project2 = projectDao.addProject(project2);

        List<Project> projectsFromDao = projectDao.getAllProjects();

        assertEquals(2, projectsFromDao.size(), "The DAO should contain 2 projects");
//        assertTrue(projectsFromDao.contains(project), "The DAO should contain project1");
        assertTrue(projectsFromDao.contains(project2), "The DAO should contain project2");


    }

    @Test
    @DisplayName("Update Project")
    void testUpdateProject(){

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

        //Create Employee2
        Employee employee2 = new Employee();
        employee2.setFirstName("Joan");
        employee2.setLastName("Reap");
        employee2.setDepartment(businessDevelop);
        employee2.setTasks(tasks);
        employee2 = employeeDao.addEmployee(employee2);

        //CreateProject
        Project project = new Project();
        project.setProjectName("Testing Project");
        project.setProjectDesc("Description");
        project.setEmployee(employee);
        project.setTasks(tasks);
        project = projectDao.addProject(project);

        Project daoProject = projectDao.getProjectByID(project.getProjectID());
        assertEquals(project, daoProject);

        //Update Project
        project.setProjectName("Updated Title");
        project.setProjectDesc("Additional Info");
        project.setEmployee(employee2);

        projectDao.updateProject(project);
        assertNotEquals(project,daoProject);

        daoProject = projectDao.getProjectByID(project.getProjectID());
        assertEquals(project, daoProject);

    }

    @Test
    @DisplayName("Delete Project")
    void testDeleteProject() {

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

        //CreateProject
        Project project = new Project();
        project.setProjectName("Testing Project");
        project.setProjectDesc("Description");
        project.setEmployee(employee);
        project.setTasks(tasks);
        project = projectDao.addProject(project);

        Project daoProject = projectDao.getProjectByID(project.getProjectID());
        assertEquals(project, daoProject);

        //Delete Project
        projectDao.deleteProjectByID(project.getProjectID());

        daoProject = projectDao.getProjectByID(project.getProjectID());

        assertNull(daoProject);

    }
}
