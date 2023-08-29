package com.kassing.taskTracker.DTO;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Department {

    private int departmentID;

    @NotBlank(message = "Department name must not be empty.")
    @Size(max = 50, message = "Department name must be less than 50 characters.")
    private String departmentName;

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return departmentID == that.departmentID && Objects.equals(departmentName, that.departmentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentID, departmentName);
    }
}
