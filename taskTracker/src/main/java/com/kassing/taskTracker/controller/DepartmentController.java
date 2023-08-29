package com.kassing.taskTracker.controller;

import com.kassing.taskTracker.DTO.Department;
import com.kassing.taskTracker.service.DepartmentDataValidationException;
import com.kassing.taskTracker.service.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class DepartmentController {

    @Autowired
    DepartmentServiceImpl departmentService;

    Set<ConstraintViolation<Department>> violations = new HashSet<>();

    @GetMapping("departments")
    public String displayAllDepartments(Model model) {

        List<Department> departments = departmentService.getAllDepartments();

        model.addAttribute("departments", departments);
        model.addAttribute("errors", violations);

        return "departments";

    }

    @PostMapping("addDepartment")
    public String addDepartment(@Valid Department department, BindingResult result, HttpServletRequest request, Model model) throws DepartmentDataValidationException {

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(department);

        if(violations.isEmpty()) {
            departmentService.addDepartment(department);
        }

        return "redirect:/departments";
    }

    @GetMapping("deleteDepartment")
    public String deleteDepartment (HttpServletRequest request) {
        int departmentID = Integer.parseInt(request.getParameter("id"));
        departmentService.deleteDepartmentByID(departmentID);

        return "redirect:/departments";
    }

    @GetMapping("editDepartment")
    public String editDepartment (Integer id, Model model) {


        Department department = departmentService.getDepartmentByID(id);

        model.addAttribute("department", department);

        return "editDepartment";
    }

    @PostMapping("editDepartment")
    public String performEditDepartment(@Valid Department department, BindingResult result, HttpServletRequest request,Model model) {

        department.setDepartmentID(department.getDepartmentID());

        String departmentName = request.getParameter("departmentName");
        department.setDepartmentName(departmentName);

        if(result.hasErrors()) {
            model.addAttribute("department", department);
            return "editDepartment";
        }




        departmentService.updateDepartment(department);

        return "redirect:/departments";
    }


}
