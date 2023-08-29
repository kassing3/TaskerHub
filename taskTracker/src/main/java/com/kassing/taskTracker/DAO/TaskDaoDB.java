package com.kassing.taskTracker.DAO;

import com.kassing.taskTracker.DAO.Mappers.*;
import com.kassing.taskTracker.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskDaoDB implements TaskDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Task getTaskByID(int taskID) {

        try {

            final String GET_TASK_BY_ID = "SELECT * FROM Task WHERE taskID = ?;";

            Task task = jdbcTemplate.queryForObject(GET_TASK_BY_ID, new TaskMapper(), taskID);

            task.setProject(setProjectForTask(taskID));
            task.setTaskPriority(setTaskPriorityForTask(taskID));
            task.setTaskStatus(setTaskStatusForTask(taskID));
            task.setEmployees(setEmployeesForTask(task));

            return task;


        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Task> getAllTasks() {

        final String GET_ALL_TASKS = "SELECT * FROM Task;";

        List<Task> tasks = jdbcTemplate.query(GET_ALL_TASKS, new TaskMapper());

        setProjectTaskPriorityTaskStatusEmployeesForTaskList(tasks);


        return tasks;
    }

    @Override
    @Transactional
    public Task addTask(Task task) {

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        final String ADD_TASK = "INSERT INTO Task (taskName, taskDesc, taskDueDate, projectID, taskPriorityTypeID, taskStatusID) VALUES (?,?,?,?,?,?);";

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    ADD_TASK,
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, task.getTaskName());
            statement.setString(2, task.getTaskDesc());
            statement.setDate(3, Date.valueOf(task.getTaskDueDate()));
            statement.setInt(4, task.getProject().getProjectID());
            statement.setInt(5, task.getTaskPriority().getTaskPriorityID());
            statement.setInt(6, task.getTaskStatus().getTaskStatusID());

            return statement;

        }, keyHolder);

        task.setTaskID(keyHolder.getKey().intValue());
        insertTaskEmployee(task);

        return task;
    }

    @Override
    public void updateTask(Task task) {

        final String UPDATE_TASK = "UPDATE Task SET taskName = ?, taskDesc = ?, taskDueDate = ?, projectID = ?, taskPriorityTypeID = ?, taskStatusID = ? WHERE taskID = ?;";

        jdbcTemplate.update(UPDATE_TASK,
                task.getTaskName(),
                task.getTaskDesc(),
                task.getTaskDueDate(),
                task.getProject().getProjectID(),
                task.getTaskPriority().getTaskPriorityID(),
                task.getTaskStatus().getTaskStatusID(),
                task.getTaskID());

        deleteTaskEmployee(task);
        insertTaskEmployee(task);
    }

    @Override
    @Transactional
    public void deleteTaskByID(int taskID) {

        final String UPDATE_TASK_EMPLOYEE = "DELETE FROM TaskEmployee WHERE taskID = ?;";
        jdbcTemplate.update(UPDATE_TASK_EMPLOYEE, taskID);


        final String  DELETE_TASK = "DELETE FROM Task WHERE taskID = ?;";
        jdbcTemplate.update(DELETE_TASK, taskID);

    }

    @Override
    public List<Task> getTasksByEmployee(Employee employee) {
        final String GET_TASKS_BY_EMPLOYEE = "SELECT t.* FROM Task t " +
                "JOIN TaskEmployee te ON te.taskID = t.taskID WHERE te.employeeID = ?;";

        List<Task> tasks = jdbcTemplate.query(GET_TASKS_BY_EMPLOYEE, new TaskMapper(), employee.getEmployeeID());

        setProjectTaskPriorityTaskStatusEmployeesForTaskList(tasks);

        return tasks;
    }

    @Override
    public List<Task> getTasksByProject(Project project) {
        final String GET_TASKS_BY_PROJECT = "SELECT * FROM Task WHERE projectID =  ?;";

        List<Task> tasks = jdbcTemplate.query(GET_TASKS_BY_PROJECT, new TaskMapper(), project.getProjectID());

        setProjectTaskPriorityTaskStatusEmployeesForTaskList(tasks);

        return tasks;
    }


    //PRIVATE HELPER FUNCTIONS
    private Project setProjectForTask(int taskID) {

        try{

            final String GET_PROJECT_FOR_TASK = "SELECT p.* FROM Project p "+
                    "JOIN Task t ON t.projectID = p.projectID " +
                    "WHERE t.taskID = ?;";

            Project project  = jdbcTemplate.queryForObject(GET_PROJECT_FOR_TASK, new ProjectMapper(), taskID);

            final String GET_EMPLOYEE_PROJECT = "SELECT e.* FROM Employee e " +
                    "JOIN Project p ON p.employeeID = e.employeeID " +
                    "WHERE p.projectID = ?;";

            Employee employee =  jdbcTemplate.queryForObject(GET_EMPLOYEE_PROJECT, new EmployeeMapper(), project.getProjectID());

            final String  GET_DEPARTMENT_EMPLOYEE = "SELECT d.* FROM Department d " +
                    "JOIN Employee e ON e.departmentID = d.departmentID " +
                    "WHERE e.employeeID = ?;";

            Department department =  jdbcTemplate.queryForObject(GET_DEPARTMENT_EMPLOYEE, new DepartmentMapper(), employee.getEmployeeID());


            final String GET_TASKS_FOR_EMPLOYEE = "SELECT t.* FROM Task t " +
                    "JOIN TaskEmployee te ON te.taskID = t.taskID " +
                    "JOIN Employee e ON te.employeeID = e.employeeID " +
                    "WHERE e.employeeID = ?;";

            List<Task> tasks = jdbcTemplate.query(
                    GET_TASKS_FOR_EMPLOYEE,
                    new TaskMapper(),
                    employee.getEmployeeID());

            employee.setDepartment(department);
            employee.setTasks(tasks);

            project.setEmployee(employee);

            return project;

        } catch (DataAccessException ex) {
            return null;
        }
    }

    private TaskPriority setTaskPriorityForTask(int taskID) {

        try{

            final String GET_TASK_PRIORITY_FOR_TASK = "SELECT p.* FROM taskPriorityType p "+
                    "JOIN Task t ON t.taskPriorityTypeID = p.taskPriorityTypeID " +
                    "WHERE t.taskID = ?;";

            return jdbcTemplate.queryForObject(GET_TASK_PRIORITY_FOR_TASK, new TaskPriorityMapper(), taskID);

        } catch (DataAccessException ex) {
            return null;
        }
    }

    private TaskStatus setTaskStatusForTask(int taskID) {

        try{

            final String GET_TASK_STATUS_FOR_TASK = "SELECT s.* FROM taskStatus s "+
                    "JOIN Task t ON t.taskStatusID = s.taskStatusID " +
                    "WHERE t.taskID = ?;";

            return jdbcTemplate.queryForObject(GET_TASK_STATUS_FOR_TASK, new TaskStatusMapper(), taskID);

        } catch (DataAccessException ex) {
            return null;
        }
    }

    private List<Employee> setEmployeesForTask(Task task){

        final String GET_EMPLOYEES_FOR_TASK = "SELECT e.* FROM Employee e " +
                "JOIN TaskEmployee te ON e.employeeID = te.employeeID " +
                "JOIN Task t ON t.taskID = te.taskID " +
                "WHERE t.taskID = ?;";

        List<Employee> employees = jdbcTemplate.query(
                GET_EMPLOYEES_FOR_TASK,
                new EmployeeMapper(),
                task.getTaskID());

        for(Employee employee : employees) {

            int employeeID = employee.getEmployeeID();

            final String  GET_DEPARTMENT_EMPLOYEE = "SELECT d.* FROM Department d " +
                    "JOIN Employee e ON e.departmentID = d.departmentID " +
                    "WHERE e.employeeID = ?;";

            Department department =  jdbcTemplate.queryForObject(GET_DEPARTMENT_EMPLOYEE, new DepartmentMapper(),employeeID);


            final String GET_TASKS_FOR_EMPLOYEE = "SELECT t.* FROM Task t " +
                    "JOIN TaskEmployee te ON te.taskID = t.taskID " +
                    "JOIN Employee e ON te.employeeID = e.employeeID " +
                    "WHERE e.employeeID = ?;";

            List<Task> tasks = jdbcTemplate.query(
                    GET_TASKS_FOR_EMPLOYEE,
                    new TaskMapper(),
                    employeeID);

            employee.setDepartment(department);
            employee.setTasks(tasks);

        }

        return employees.size() == 0 ? new ArrayList<>(): employees;
    }

    private void setProjectTaskPriorityTaskStatusEmployeesForTaskList(List<Task> tasks) {

        for(Task task : tasks) {

            int taskID = task.getTaskID();

            task.setProject(setProjectForTask(taskID));
            task.setTaskPriority(setTaskPriorityForTask(taskID));
            task.setTaskStatus(setTaskStatusForTask(taskID));
            task.setEmployees(setEmployeesForTask(task));

        }
    }

    private void insertTaskEmployee(Task task) {
        if(task.getEmployees() != null) {
            final String INSERT_TASK_EMPLOYEE = "INSERT INTO "
                    + "TaskEmployee(taskID, employeeID) VALUES (?,?);";

            for(Employee employee : task.getEmployees()) {


                jdbcTemplate.update(INSERT_TASK_EMPLOYEE,
                        task.getTaskID(),
                        employee.getEmployeeID());
            }
        }
    }

    private void deleteTaskEmployee(Task task) {

        final String DELETE_TASK_EMPLOYEE= "DELETE FROM TaskEmployee WHERE taskID = (?)";


        jdbcTemplate.update(DELETE_TASK_EMPLOYEE, task.getTaskID());
    }



}
