package com.kassing.taskTracker.DTO;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Task {

    private int taskID;


    @NotBlank(message = "Task name must not be empty.")
    @Size(max = 50, message = "Task name must be less than 50 characters.")
    private String taskName;

    @NotBlank(message = "Task description must not be empty.")
    @Size(max = 255, message = "Task description must be less than 255 characters.")
    private String taskDesc;

    @DateTimeFormat
    private LocalDate taskDueDate;

    private Project project;
    private TaskPriority taskPriority;
    private TaskStatus taskStatus;
    private List<Employee> employees;

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public LocalDate getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(LocalDate taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskID == task.taskID && Objects.equals(taskName, task.taskName) && Objects.equals(taskDesc, task.taskDesc) && Objects.equals(taskDueDate, task.taskDueDate) && Objects.equals(project, task.project) && Objects.equals(taskPriority, task.taskPriority) && Objects.equals(taskStatus, task.taskStatus) && Objects.equals(employees, task.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskID, taskName, taskDesc, taskDueDate, project, taskPriority, taskStatus, employees);
    }
}
