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
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeDaoDB implements EmployeeDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Employee getEmployeeByID(int employeeID) {

        try {

            final String GET_EMPLOYEE_BY_ID = "SELECT * FROM Employee WHERE employeeID = ?;";

            Employee employee = jdbcTemplate.queryForObject(GET_EMPLOYEE_BY_ID, new EmployeeMapper(), employeeID);

            employee.setDepartment(getDepartmentForEmployee(employeeID));
            employee.setTasks(getTasksForEmployee(employee));

            return employee;

        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Employee> getAllEmployees() {

        final String GET_ALL_EMPLOYEES = "SELECT * FROM Employee;";

        List<Employee> employees = jdbcTemplate.query(GET_ALL_EMPLOYEES, new EmployeeMapper());

        setDepartmentAndTasksForEmployeeList(employees);

        return employees;
    }

    @Override
    @Transactional
    public Employee addEmployee(Employee employee) {

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        final String ADD_EMPLOYEE = "INSERT INTO Employee (firstName, lastName, departmentID) VALUES (?,?,?);";

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    ADD_EMPLOYEE,
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setInt(3, employee.getDepartment().getDepartmentID());

            return statement;
        }, keyHolder);

        employee.setEmployeeID(keyHolder.getKey().intValue());

        insertTaskEmployee(employee);

        return employee;
    }

    @Override
    public void updateEmployee(Employee employee) {

        final String UPDATE_EMPLOYEE = "UPDATE Employee SET firstName = ?, lastName = ?, departmentID = ? WHERE employeeID = ?;";

        jdbcTemplate.update(UPDATE_EMPLOYEE,
                employee.getFirstName(),
                employee.getLastName(),
                employee.getDepartment().getDepartmentID(),
                employee.getEmployeeID());

        final String DELETE_TASK_EMPLOYEE= "DELETE FROM TaskEmployee WHERE employeeID = ?";

        jdbcTemplate.update(DELETE_TASK_EMPLOYEE, employee.getEmployeeID());

        insertTaskEmployee(employee);
    }

    @Override
    @Transactional
    public void deleteEmployeeByID(int employeeID) {

        final String UPDATE_EMPLOYEE_TASK = "DELETE FROM TaskEmployee WHERE employeeID = ?;";
        jdbcTemplate.update(UPDATE_EMPLOYEE_TASK, employeeID);

        final String UPDATE_EMPLOYEE_PROJECT = "UPDATE Project SET employeeID = NULL WHERE employeeID = ?;";
        jdbcTemplate.update(UPDATE_EMPLOYEE_PROJECT, employeeID);

        final String DELETE_EMPLOYEE = "DELETE FROM Employee WHERE employeeID = ?;";
        jdbcTemplate.update(DELETE_EMPLOYEE, employeeID);

    }


    //PRIVATE HELPER FUNCTIONS

    private Department getDepartmentForEmployee(int employeeID){

        try {

            final String  GET_DEPARTMENT_FOR_EMPLOYEE = "SELECT d.* FROM Department d "+
                    "JOIN Employee e ON e.departmentID = d.departmentID " +
                    "WHERE e.employeeID = ?;";

            return jdbcTemplate.queryForObject(GET_DEPARTMENT_FOR_EMPLOYEE, new DepartmentMapper(), employeeID);
        } catch (DataAccessException ex) {
            return null;
        }


    }

    private List<Task> getTasksForEmployee(Employee employee){

        final String GET_TASKS_FOR_EMPLOYEE = "SELECT t.* FROM Task t " +
                "JOIN TaskEmployee te ON te.taskID = t.taskID " +
                "JOIN Employee e ON te.employeeID = e.employeeID " +
                "WHERE e.employeeID = ?;";

        List<Task> tasks = jdbcTemplate.query(
                GET_TASKS_FOR_EMPLOYEE,
                new TaskMapper(),
                employee.getEmployeeID());

        getProjectStatusPriorityForTaskList(tasks);

        return tasks.size() == 0 ? new ArrayList<>(): tasks;
    }

    /*
     * Set the Department & tasks for a list of Employees
     * @param List employees
     */

    private void setDepartmentAndTasksForEmployeeList (List<Employee> employees) {

        for (Employee employee : employees) {
            int employeeID = employee.getEmployeeID();
            employee.setDepartment(getDepartmentForEmployee(employeeID));
            employee.setTasks(getTasksForEmployee(employee));
        }
    }

    private Project getProjectForTask(int taskID){

        try {

            final String  GET_PROJECT_FOR_TASK = "SELECT p.* FROM Project p "+
                    "JOIN Task t ON t.projectID = p.projectID " +
                    "WHERE t.taskID = ?;";

            return jdbcTemplate.queryForObject(GET_PROJECT_FOR_TASK, new ProjectMapper(), taskID);
        } catch (DataAccessException ex) {
            return null;
        }


    }

    private TaskStatus getStatusForTask(int taskID){

        try {

            final String GET_TASK_STATUS_FOR_TASK = "SELECT s.* FROM taskStatus s "+
                    "JOIN Task t ON t.taskStatusID = s.taskStatusID " +
                    "WHERE t.taskID = ?;";

            return jdbcTemplate.queryForObject(GET_TASK_STATUS_FOR_TASK, new TaskStatusMapper(), taskID);

        } catch (DataAccessException ex) {
            return null;
        }


    }

    private TaskPriority getPriorityForTask(int taskID){

        try {

            final String GET_TASK_PRIORITY_FOR_TASK = "SELECT p.* FROM taskPriorityType p "+
                    "JOIN Task t ON t.taskPriorityTypeID = p.taskPriorityTypeID " +
                    "WHERE t.taskID = ?;";

            return jdbcTemplate.queryForObject(GET_TASK_PRIORITY_FOR_TASK, new TaskPriorityMapper(), taskID);

        } catch (DataAccessException ex) {
            return null;
        }


    }

    /*
     * Set the Project, Status & Priority for a list of tasks
     * @param List tasks
     */
    private void getProjectStatusPriorityForTaskList (List<Task> tasks) {

        for (Task task : tasks) {
            int taskID = task.getTaskID();
            task.setProject(getProjectForTask(taskID));
            task.setTaskStatus(getStatusForTask(taskID));
            task.setTaskPriority(getPriorityForTask(taskID));
        }
    }

    private void insertTaskEmployee(Employee employee) {

        if(employee.getTasks() != null) {

            final String INSERT_TASK_EMPLOYEE = "INSERT INTO TaskEmployee(taskID, employeeID) VALUES (?,?);";

            for(Task task : employee.getTasks()) {
                jdbcTemplate.update(INSERT_TASK_EMPLOYEE,
                        task.getTaskID(),
                        employee.getEmployeeID());
            }
        }
    }


}
