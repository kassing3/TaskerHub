package com.kassing.taskTracker.DTO;

import java.util.Objects;

public class TaskPriority {

    private int taskPriorityID;
    private String taskPriorityType;
    private String  taskPriorityDesc;

    public int getTaskPriorityID() {
        return taskPriorityID;
    }

    public void setTaskPriorityID(int taskPriorityID) {
        this.taskPriorityID = taskPriorityID;
    }

    public String getTaskPriorityType() {
        return taskPriorityType;
    }

    public void setTaskPriorityType(String taskPriorityType) {
        this.taskPriorityType = taskPriorityType;
    }

    public String getTaskPriorityDesc() {
        return taskPriorityDesc;
    }

    public void setTaskPriorityDesc(String taskPriorityDesc) {
        this.taskPriorityDesc = taskPriorityDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskPriority that = (TaskPriority) o;
        return taskPriorityID == that.taskPriorityID && Objects.equals(taskPriorityType, that.taskPriorityType) && Objects.equals(taskPriorityDesc, that.taskPriorityDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskPriorityID, taskPriorityType, taskPriorityDesc);
    }
}
