package com.assessment.sms.academics.service;

import com.assessment.sms.commons.model.PaginationRequest;
import com.assessment.sms.studentacademics.model.DepartmentModel;
import com.assessment.sms.studentacademics.repo.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("departmentService")
public class DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    public List<DepartmentModel> getAllDepartments(PaginationRequest paginationRequest) {
        return departmentRepo.getAllDepartments(paginationRequest);
    }

    public DepartmentModel getDepartmentByCode(String deptCode) {
        return departmentRepo.getDepartmentByCode(deptCode);
    }

    public List<DepartmentModel> getDepartmentsByName(String departmentType) {
        return departmentRepo.getDepartmentsByType(departmentType);
    }

    public List<DepartmentModel> createDepartments(List<DepartmentModel> departments) {
        return departmentRepo.createDepartments(departments);
    }

    public List<DepartmentModel> updateDepartments(List<DepartmentModel> departments) {
        return departmentRepo.updateDepartments(departments);
    }

    public long deleteDepartment(List<String> ids) {
        return departmentRepo.deleteDepartments(ids);
    }
}
