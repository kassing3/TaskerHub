package com.kassing.taskTracker.DAO;

import com.kassing.taskTracker.DTO.TaskPriority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskPriorityDBTest {

    @Autowired
    TaskPriorityDao taskPriorityDao;

    public TaskPriorityDBTest()  {

    }

    @BeforeEach
    public void setUpClass(){

        //Clear Any Created Task Priorities from previous tests
        List<TaskPriority> taskPriorities = taskPriorityDao.getAllTaskPriorities();
        for(TaskPriority taskPriority : taskPriorities) {
            taskPriorityDao.deleteTaskPriorityByID(taskPriority.getTaskPriorityID());
        }

    }

    @Test
    @DisplayName("Get and Test TaskPriority")
    void testGetAndAddTaskPriority(){

        //Create Do Task Priority Type
        TaskPriority doPriorityType = new TaskPriority();
        doPriorityType.setTaskPriorityType("Do");
        doPriorityType.setTaskPriorityDesc("An urgent and important task with deadlines or consequences.");
        doPriorityType = taskPriorityDao.addTaskPriority(doPriorityType);

        //Pull Test Priority from Dao
        TaskPriority testPriority = taskPriorityDao.getTaskPriorityByID(doPriorityType.getTaskPriorityID());

        //Confirms Priorities are Equal
        assertEquals(doPriorityType, doPriorityType);

    }

    @Test
    @DisplayName("Get All Task Priorities")
    void testGetAllTaskPriorities(){

        //Create Plan Task Priority Type
        TaskPriority planPriorityType = new TaskPriority();
        planPriorityType.setTaskPriorityType("Plan");
        planPriorityType.setTaskPriorityDesc("A non-urgent and important task with unclear deadlines that contribute to long-term success.");
        planPriorityType = taskPriorityDao.addTaskPriority(planPriorityType);

        //Create Delegate Task Priority Type
        TaskPriority delegatePriorityType = new TaskPriority();
        delegatePriorityType.setTaskPriorityType("Delegate");
        delegatePriorityType.setTaskPriorityDesc("An urgent and unimportant task that must get done, but don't require you specific skill set.");
        delegatePriorityType = taskPriorityDao.addTaskPriority(delegatePriorityType);

        //Create Delete Task Priority Type
        TaskPriority deletePriorityType = new TaskPriority();
        deletePriorityType.setTaskPriorityType("Delete");
        deletePriorityType.setTaskPriorityDesc("A non urgent and unimportant task that is unnecessary..");
        deletePriorityType = taskPriorityDao.addTaskPriority(deletePriorityType);

        List<TaskPriority> taskPrioritiesFromDao = taskPriorityDao.getAllTaskPriorities();

        assertEquals(3, taskPrioritiesFromDao.size(),"The DAO should contain 3 priorities.");
        assertTrue(taskPrioritiesFromDao.contains(planPriorityType), "The DAO should contain planPriorityType");
        assertTrue(taskPrioritiesFromDao.contains(delegatePriorityType), "The DAO should contain delegatePriorityType");
        assertTrue(taskPrioritiesFromDao.contains(deletePriorityType), "The DAO should contain deletePriorityType");

    }

    @Test
    @DisplayName("Update Task Priority")
    void testUpdateTaskPriority() {

        //Create Do Task Priority Type
        TaskPriority doPriorityType = new TaskPriority();
        doPriorityType.setTaskPriorityType("Do");
        doPriorityType.setTaskPriorityDesc("An urgent and important task with deadlines or consequences.");
        doPriorityType = taskPriorityDao.addTaskPriority(doPriorityType);

        //Pull Test Priority from Dao
        TaskPriority testPriority = taskPriorityDao.getTaskPriorityByID(doPriorityType.getTaskPriorityID());

        //Confirms Priorities are Equal
        assertEquals(doPriorityType, testPriority);

        //Update Task Priority
        doPriorityType.setTaskPriorityType("Updated Priority");
        doPriorityType.setTaskPriorityDesc("Updated Desc");
        taskPriorityDao.updateTaskPriority(doPriorityType);

        //Confirms Updated Priority and created prriority are not equal
        assertNotEquals(doPriorityType, testPriority);

        testPriority = taskPriorityDao.getTaskPriorityByID(doPriorityType.getTaskPriorityID());
        assertEquals(doPriorityType, testPriority);

    }

    @Test
    @DisplayName("Delete Task Priority By ID")
    void testDeleteTaskPriorityByID(){

        TaskPriority doPriorityType = new TaskPriority();
        doPriorityType.setTaskPriorityType("Do");
        doPriorityType.setTaskPriorityDesc("An urgent and important task with deadlines or consequences.");
        doPriorityType = taskPriorityDao.addTaskPriority(doPriorityType);

        //Pull Test Priority from Dao
        TaskPriority testPriority = taskPriorityDao.getTaskPriorityByID(doPriorityType.getTaskPriorityID());

        //Confirms Priorities are Equal
        assertEquals(doPriorityType, testPriority);

        //Delete Task Priority
        taskPriorityDao.deleteTaskPriorityByID(doPriorityType.getTaskPriorityID());

        //Attempt to pull Deleted Status
        testPriority = taskPriorityDao.getTaskPriorityByID(doPriorityType.getTaskPriorityID());

        assertNull(testPriority);

    }




}
