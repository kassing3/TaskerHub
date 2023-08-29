package com.kassing.taskTracker.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class Employee {

    private int employeeID;

    @NotBlank(message = "First name must not be empty.")
    @Size(max = 50, message = "First name must be less than 50 characters.")
    private String firstName;

    @NotBlank(message = "Last name must not be empty.")
    @Size(max = 50, message = "Last name must be less than 50 characters.")
    private String lastName;
    private Department department;
    private List<Task> tasks;

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
        Employee employee = (Employee) o;
        return employeeID == employee.employeeID && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(department, employee.department) && Objects.equals(tasks, employee.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeID, firstName, lastName, department, tasks);
    }
}
