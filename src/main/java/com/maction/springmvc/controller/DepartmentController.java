package com.maction.springmvc.controller;

import com.maction.springmvc.model.Department;
import com.maction.springmvc.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    public List<Department> getAllDepartment() {
        return departmentService.getAllDepartments();
    }
}
