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
public class ProjectDaoDB implements ProjectDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Project getProjectByID(int projectID) {

        try{

            final String GET_PROJECT_BY_ID = "SELECT * FROM Project where projectID = ?;";

            Project project = jdbcTemplate.queryForObject(GET_PROJECT_BY_ID, new ProjectMapper(), projectID);

            project.setEmployee(getEmployeeForProject(projectID));
            project.setTasks(getTasksForProject(project));

            return project;

        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Project> getAllProjects() {

        final String GET_ALL_PROJECTS = "SELECT * FROM Project;";

        List<Project> projects = jdbcTemplate.query(GET_ALL_PROJECTS, new ProjectMapper());

        setEmployeeAndTasksForProjectList(projects);

        return projects;
    }

    @Override
    @Transactional
    public Project addProject(Project project) {

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        final String ADD_PROJECT = "INSERT INTO Project (projectName, projectDesc, employeeID) VALUES (?,?,?);";

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    ADD_PROJECT,
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, project.getProjectName());
            statement.setString(2, project.getProjectDesc());
            statement.setInt(3, project.getEmployee().getEmployeeID());

            return statement;

        }, keyHolder);

        project.setProjectID(keyHolder.getKey().intValue());

        return project;
    }

    @Override
    public void updateProject(Project project) {

        final String UPDATE_PROJECT = "UPDATE Project set projectName = ?, projectDesc = ?, employeeID =? WHERE projectID = ?;";

        jdbcTemplate.update(UPDATE_PROJECT,
                project.getProjectName(),
                project.getProjectDesc(),
                project.getEmployee().getEmployeeID(),
                project.getProjectID());

    }

    @Override
    @Transactional
    public void deleteProjectByID(int projectID) {

        final String UPDATE_PROJECT = "UPDATE Task SET projectID = NULL WHERE projectID = ?;";
        jdbcTemplate.update(UPDATE_PROJECT, projectID);

        final String DELETE_PROJECT = "DELETE FROM Project WHERE projectID = ?;";
        jdbcTemplate.update(DELETE_PROJECT, projectID);

    }

    //Private Helper Functions

    private Employee getEmployeeForProject(int projectID) {

        try{

            final String  GET_EMPLOYEE_PROJECT = "SELECT e.* FROM Employee e " +
                    "JOIN Project p ON p.employeeID = e.employeeID " +
                    "WHERE p.projectID = ?;";

            Employee employee = jdbcTemplate.queryForObject(GET_EMPLOYEE_PROJECT, new EmployeeMapper(),projectID);

            final String  GET_DEPARTMENT_EMPLOYEE = "SELECT d.* FROM Department d " +
                    "JOIN Employee e ON e.departmentID = d.departmentID " +
                    "WHERE e.employeeID = ?;";

            Department department =  jdbcTemplate.queryForObject(GET_DEPARTMENT_EMPLOYEE, new DepartmentMapper(),employee.getEmployeeID());


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

            return employee;

        } catch (DataAccessException ex) {
            return null;
        }

    }

    private void  setEmployeeAndTasksForProjectList(List<Project> projects) {

        for (Project project : projects) {
            int projectId = project.getProjectID();
            project.setEmployee(getEmployeeForProject(projectId));
            project.setTasks(getTasksForProject(project));
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

    private void getProjectStatusPriorityForTaskList (List<Task> tasks) {

        for (Task task : tasks) {
            int taskID = task.getTaskID();
            task.setProject(getProjectForTask(taskID));
            task.setTaskStatus(getStatusForTask(taskID));
            task.setTaskPriority(getPriorityForTask(taskID));
        }
    }
    private List<Task> getTasksForProject(Project project){

        final String GET_TASKS_FOR_PROJECT = "SELECT t.* FROM Task t " +
                "JOIN Project p ON p.projectID = t.projectID " +
                "WHERE p.projectID = ?;";

        List<Task> tasks = jdbcTemplate.query(
                GET_TASKS_FOR_PROJECT,
                new TaskMapper(),
                project.getProjectID());

        getProjectStatusPriorityForTaskList(tasks);

        return tasks.size() == 0 ? new ArrayList<>(): tasks;
    }
}
