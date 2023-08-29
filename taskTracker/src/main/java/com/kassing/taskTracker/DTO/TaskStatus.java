package com.kassing.taskTracker.DTO;

import java.util.Objects;

public class TaskStatus {

    private int taskStatusID;
    private String taskStatusName;
    private String taskStatusDesc;

    public int getTaskStatusID() {
        return taskStatusID;
    }

    public void setTaskStatusID(int taskStatusID) {
        this.taskStatusID = taskStatusID;
    }

    public String getTaskStatusName() {
        return taskStatusName;
    }

    public void setTaskStatusName(String taskStatusName) {
        this.taskStatusName = taskStatusName;
    }

    public String getTaskStatusDesc() {
        return taskStatusDesc;
    }

    public void setTaskStatusDesc(String taskStatusDesc) {
        this.taskStatusDesc = taskStatusDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskStatus that = (TaskStatus) o;
        return taskStatusID == that.taskStatusID && Objects.equals(taskStatusName, that.taskStatusName) && Objects.equals(taskStatusDesc, that.taskStatusDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskStatusID, taskStatusName, taskStatusDesc);
    }
}
