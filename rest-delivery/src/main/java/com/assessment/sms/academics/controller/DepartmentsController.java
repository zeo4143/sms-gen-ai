package com.assessment.sms.academics.controller;

import com.assessment.sms.academics.service.DepartmentService;
import com.assessment.sms.commons.model.PaginationRequest;
import com.assessment.sms.studentacademics.model.DepartmentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentsController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public List<DepartmentModel> getDepartments(
            @RequestParam Integer pageNo,
            @RequestParam Integer pageSize
    ) {
        PaginationRequest paginationRequest = new PaginationRequest(pageNo, pageSize);
        return departmentService.getAllDepartments(paginationRequest);
    }


    @GetMapping("/{depCode}")
    public DepartmentModel getDepartmentById(@PathVariable String depCode) {
        return departmentService.getDepartmentByCode(depCode);
    }

    @GetMapping("/{deptType}/list")
    public List<DepartmentModel> getDepartmentsByName(@PathVariable String deptType) {
        return departmentService.getDepartmentsByName(deptType);
    }

    @PostMapping
    public List<DepartmentModel> createDepartment(@RequestBody List<DepartmentModel> departmentModels) {
        return departmentService.createDepartments(departmentModels);
    }

    @PutMapping
    public List<DepartmentModel> updateDepartment(@RequestBody List<DepartmentModel> departmentModels) {
        return departmentService.updateDepartments(departmentModels);
    }

    @DeleteMapping
    public long deleteDepartment(@RequestBody List<String> departmentIds) {
        return departmentService.deleteDepartment(departmentIds);
    }

}
