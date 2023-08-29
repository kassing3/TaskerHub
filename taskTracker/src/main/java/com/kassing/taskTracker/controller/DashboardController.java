package com.kassing.taskTracker.controller;

import com.kassing.taskTracker.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    TaskService taskService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    TaskStatusService taskStatusService;
    @Autowired
    TaskPriorityServiceImpl taskPriorityService;

    @Autowired
    ProjectService projectService;

    @GetMapping("/")
    public String displayHomePage(Model model) {

        return "redirect:/index.html";
    }
}
