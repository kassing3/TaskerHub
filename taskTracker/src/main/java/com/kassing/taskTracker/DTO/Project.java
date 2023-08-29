package com.kassing.taskTracker.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class Project {

    private int projectID;


    @NotBlank(message = "Project name must not be empty.")
    @Size(max = 50, message = "Project name must be less than 50 characters.")
    private String projectName;

    @NotBlank(message = "Project description must not be empty.")
    @Size(max = 255, message = "Project description must be less than 255 characters.")
    private String projectDesc;

    private Employee employee;

    private List<Task> tasks;

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return projectID == project.projectID && Objects.equals(projectName, project.projectName) && Objects.equals(projectDesc, project.projectDesc) && Objects.equals(employee, project.employee) && Objects.equals(tasks, project.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectID, projectName, projectDesc, employee, tasks);
    }
}
