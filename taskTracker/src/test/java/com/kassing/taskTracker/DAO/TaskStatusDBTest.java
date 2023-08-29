package com.kassing.taskTracker.DAO;

import com.kassing.taskTracker.DTO.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskStatusDBTest {
    
    @Autowired
    TaskStatusDao taskStatusDao;
    
    public TaskStatusDBTest()  {
        
    }
    
    @BeforeEach
    public void setUpClass(){

        //Clear Any Created Task Statuses
        List<TaskStatus> taskStatuses = taskStatusDao.getAllTaskStatuses();
        for(TaskStatus taskStatus : taskStatuses) {
            taskStatusDao.deleteTaskStatus(taskStatus.getTaskStatusID());
        }

    }

    @Test
    @DisplayName("Get and Add TaskStatus")
    void testGetAndAddTaskStatus(){

        //Create Not Started Task Status
        TaskStatus notStartedStatus = new TaskStatus();
        notStartedStatus.setTaskStatusName("Not Started");
        notStartedStatus.setTaskStatusDesc("This task has not been started yet.");
        notStartedStatus = taskStatusDao.addTaskStatus(notStartedStatus);

        //Pull Test Status from Dao
        TaskStatus testStatus = taskStatusDao.getTaskStatusByID(notStartedStatus.getTaskStatusID());

        assertEquals(notStartedStatus, testStatus);

    }

    @Test
    @DisplayName("Get All Task Statuses")
    void testGetAllTaskStatuses(){

        //Create In-Progress  Task Status
        TaskStatus inProgressStatus = new TaskStatus();
        inProgressStatus.setTaskStatusName("In-Progress");
        inProgressStatus.setTaskStatusDesc("This task is currently in the progress of being completed.");
        inProgressStatus = taskStatusDao.addTaskStatus(inProgressStatus);

        //Create On Hold Task Status
        TaskStatus onHoldStatus = new TaskStatus();
        onHoldStatus.setTaskStatusName("On Hold");
        onHoldStatus.setTaskStatusDesc("This task is paused until further information has been provided or additional steps taken.");
        onHoldStatus = taskStatusDao.addTaskStatus(onHoldStatus);

        //Create Completed Task Status
        TaskStatus completeStatus = new TaskStatus();
        completeStatus.setTaskStatusName("On Hold");
        completeStatus.setTaskStatusDesc("This task has been completed.");
        completeStatus = taskStatusDao.addTaskStatus(completeStatus);

        List<TaskStatus> taskStatusesFromDao = taskStatusDao.getAllTaskStatuses();

        assertEquals(3, taskStatusesFromDao.size(),"The DAO should contain 3 statuses.");
        assertTrue(taskStatusesFromDao.contains(inProgressStatus), "The DAO should contain inProgressStatus");
        assertTrue(taskStatusesFromDao.contains(onHoldStatus), "The DAO should contain onHoldStatus");
        assertTrue(taskStatusesFromDao.contains(completeStatus), "The DAO should contain completeStatus");

    }

    @Test
    @DisplayName("Update Task Status")
    void testUpdateTaskStatus() {

        //Create Overdue Task Status
        TaskStatus overdueStatus = new TaskStatus();
        overdueStatus.setTaskStatusName("Overdue");
        overdueStatus.setTaskStatusDesc("This task passed it's deadline and must be addressed immediately.");
        overdueStatus = taskStatusDao.addTaskStatus(overdueStatus);

        //Pull Status From Dao
        TaskStatus testUpdatedStatus = taskStatusDao.getTaskStatusByID(overdueStatus.getTaskStatusID());

        //Confirms Status are Equal
        assertEquals(overdueStatus, testUpdatedStatus);

        //Update Tasks Status
        overdueStatus.setTaskStatusName("Updated Status");
        overdueStatus.setTaskStatusDesc("Updated Desc");
        taskStatusDao.updateTaskStatus(overdueStatus);

        //Confirms Updated Status and created status are not equal
        assertNotEquals(overdueStatus, testUpdatedStatus);

        testUpdatedStatus = taskStatusDao.getTaskStatusByID(overdueStatus.getTaskStatusID());
        assertEquals(overdueStatus, testUpdatedStatus);

    }

    @Test
    @DisplayName("Delete Task StatusByID")
    void testDeleteTaskStatusByID(){

        //Create Overdue Task Status
        TaskStatus overdueStatus = new TaskStatus();
        overdueStatus.setTaskStatusName("Overdue");
        overdueStatus.setTaskStatusDesc("This task passed it's deadline and must be addressed immediately.");
        overdueStatus = taskStatusDao.addTaskStatus(overdueStatus);

        //Pull Status From Dao
        TaskStatus testUpdatedStatus = taskStatusDao.getTaskStatusByID(overdueStatus.getTaskStatusID());

        //Confirms Status are Equal
        assertEquals(overdueStatus, testUpdatedStatus);

        //Delete Task Status
        taskStatusDao.deleteTaskStatus(overdueStatus.getTaskStatusID());

        //Attempt to pull Deleted Status
        testUpdatedStatus = taskStatusDao.getTaskStatusByID(overdueStatus.getTaskStatusID());

        assertNull(testUpdatedStatus);

    }

    
    
}
