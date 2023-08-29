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
public class TaskDBTest {

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

    public TaskDBTest() {


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
    @DisplayName("Get and Add Task")
    void  testGetAndAddTask() {

        //Create TaskPriority
        TaskPriority doPriorityType = new TaskPriority();
        doPriorityType.setTaskPriorityType("Do");
        doPriorityType.setTaskPriorityDesc("An urgent and important task with deadlines or consequences.");
        doPriorityType = taskPriorityDao.addTaskPriority(doPriorityType);

        //Create Task Status
        TaskStatus completeStatus = new TaskStatus();
        completeStatus.setTaskStatusName("On Hold");
        completeStatus.setTaskStatusDesc("This task has been completed.");
        completeStatus = taskStatusDao.addTaskStatus(completeStatus);

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

        List<Employee> employees = new ArrayList<>();



        //Create Project
        Project project = new Project();
        project.setProjectName("Launch Task Tracker Product");
        project.setProjectDesc("The purpose of this project is to launch a productivity tool to help with project management");
        project.setEmployee(employee);
        project = projectDao.addProject(project);

        //Create Task
        Task task = new Task();
        task.setTaskName("Testing Tasks");
        task.setTaskDesc("Task Desc");
        task.setTaskDueDate(LocalDate.parse("2023-03-03"));
        task.setProject(project);
        task.setTaskPriority(doPriorityType);
        task.setTaskStatus(completeStatus);
        task.setEmployees(employees);
        task = taskDao.addTask(task);



        Task taskFromDao = taskDao.getTaskByID(task.getTaskID());

        assertEquals(task,taskFromDao);


    }

    @Test
    @DisplayName("Get All Tasks")
    void testGetAllTasks() {

        //Create TaskPriority
        TaskPriority doPriorityType = new TaskPriority();
        doPriorityType.setTaskPriorityType("Do");
        doPriorityType.setTaskPriorityDesc("An urgent and important task with deadlines or consequences.");
        doPriorityType = taskPriorityDao.addTaskPriority(doPriorityType);

        //Create Task Status
        TaskStatus completeStatus = new TaskStatus();
        completeStatus.setTaskStatusName("On Hold");
        completeStatus.setTaskStatusDesc("This task has been completed.");
        completeStatus = taskStatusDao.addTaskStatus(completeStatus);

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

        List<Employee> employees = new ArrayList<>();

        //Create Project
        Project project = new Project();
        project.setProjectName("Launch Task Tracker Product");
        project.setProjectDesc("The purpose of this project is to launch a productivity tool to help with project management");
        project.setEmployee(employee);
        project = projectDao.addProject(project);

        //Create Task1
        Task task1 = new Task();
        task1.setTaskName("Testing Tasks");
        task1.setTaskDesc("Task Desc");
        task1.setTaskDueDate(LocalDate.parse("2023-03-03"));
        task1.setProject(project);
        task1.setTaskPriority(doPriorityType);
        task1.setTaskStatus(completeStatus);
        task1.setEmployees(employees);
        task1 = taskDao.addTask(task1);

        //Create Task2
        Task task2 = new Task();
        task2.setTaskName("Second Task");
        task2.setTaskDesc("Task Desc or 2");
        task2.setTaskDueDate(LocalDate.parse("2023-04-03"));
        task2.setProject(project);
        task2.setTaskPriority(doPriorityType);
        task2.setTaskStatus(completeStatus);
        task2.setEmployees(employees);
        task2 = taskDao.addTask(task2);


        List<Task> tasksFromDao = taskDao.getAllTasks();


        assertEquals(2, tasksFromDao.size(), "The Dao should contain 2 tasks");
        assertTrue(tasksFromDao.contains(task1), "The Dao should contain task1");
        assertTrue(tasksFromDao.contains(task2), "The Dao should contain task2");
    }

    @Test
    @DisplayName("Update Task")
    void testUpdateTask(){
        //Create TaskPriority
        TaskPriority doPriorityType = new TaskPriority();
        doPriorityType.setTaskPriorityType("Do");
        doPriorityType.setTaskPriorityDesc("An urgent and important task with deadlines or consequences.");
        doPriorityType = taskPriorityDao.addTaskPriority(doPriorityType);

        //Create Task Status
        TaskStatus completeStatus = new TaskStatus();
        completeStatus.setTaskStatusName("On Hold");
        completeStatus.setTaskStatusDesc("This task has been completed.");
        completeStatus = taskStatusDao.addTaskStatus(completeStatus);

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

        List<Employee> employees = new ArrayList<>();



        //Create Project
        Project project = new Project();
        project.setProjectName("Launch Task Tracker Product");
        project.setProjectDesc("The purpose of this project is to launch a productivity tool to help with project management");
        project.setEmployee(employee);

        project = projectDao.addProject(project);
        //Create Project2
        Project project2 = new Project();
        project2.setProjectName("New Project");
        project2.setProjectDesc("Project Desc");
        project2.setEmployee(employee);
        project2 = projectDao.addProject(project);


        //Create Task
        Task task = new Task();
        task.setTaskName("Testing Tasks");
        task.setTaskDesc("Task Desc");
        task.setTaskDueDate(LocalDate.parse("2023-03-03"));
        task.setProject(project);
        task.setTaskPriority(doPriorityType);
        task.setTaskStatus(completeStatus);
        task.setEmployees(employees);
        task = taskDao.addTask(task);



        Task taskFromDao = taskDao.getTaskByID(task.getTaskID());

        assertEquals(task,taskFromDao);

        //Update Task
        task.setTaskName("Updated task");
        task.setTaskDesc("New Desc");
        task.setTaskDueDate(LocalDate.parse("2023-04-03"));
        task.setProject(project2);

        taskDao.updateTask(task);
        assertNotEquals(task, taskFromDao);

        taskFromDao = taskDao.getTaskByID(task.getTaskID());
        assertEquals(task, taskFromDao);

    }

    @Test
    @DisplayName("Delete Task")
    void testDeleteTask(){
        //Create TaskPriority
        TaskPriority doPriorityType = new TaskPriority();
        doPriorityType.setTaskPriorityType("Do");
        doPriorityType.setTaskPriorityDesc("An urgent and important task with deadlines or consequences.");
        doPriorityType = taskPriorityDao.addTaskPriority(doPriorityType);

        //Create Task Status
        TaskStatus completeStatus = new TaskStatus();
        completeStatus.setTaskStatusName("On Hold");
        completeStatus.setTaskStatusDesc("This task has been completed.");
        completeStatus = taskStatusDao.addTaskStatus(completeStatus);

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

        List<Employee> employees = new ArrayList<>();



        //Create Project
        Project project = new Project();
        project.setProjectName("Launch Task Tracker Product");
        project.setProjectDesc("The purpose of this project is to launch a productivity tool to help with project management");
        project.setEmployee(employee);

        project = projectDao.addProject(project);
        //Create Project2
        Project project2 = new Project();
        project2.setProjectName("New Project");
        project2.setProjectDesc("Project Desc");
        project2.setEmployee(employee);
        project2 = projectDao.addProject(project);


        //Create Task
        Task task = new Task();
        task.setTaskName("Testing Tasks");
        task.setTaskDesc("Task Desc");
        task.setTaskDueDate(LocalDate.parse("2023-03-03"));
        task.setProject(project);
        task.setTaskPriority(doPriorityType);
        task.setTaskStatus(completeStatus);
        task.setEmployees(employees);
        task = taskDao.addTask(task);



        Task taskFromDao = taskDao.getTaskByID(task.getTaskID());

        assertEquals(task,taskFromDao);

        //Delete Task
        taskDao.deleteTaskByID(task.getTaskID());
        taskFromDao = taskDao.getTaskByID(task.getTaskID());

        assertNull(taskFromDao);
    }
}
