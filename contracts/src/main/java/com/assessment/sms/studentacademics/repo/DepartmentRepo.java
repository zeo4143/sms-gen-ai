package com.assessment.sms.studentacademics.repo;

import com.assessment.sms.commons.model.PaginationRequest;
import com.assessment.sms.studentacademics.model.DepartmentModel;

import java.util.List;

public interface DepartmentRepo {

    List<DepartmentModel> getAllDepartments(PaginationRequest paginationRequest);

    List<DepartmentModel> getDepartmentsByType(String deptType);

    DepartmentModel getDepartmentByCode(String deptCode);

    List<DepartmentModel> createDepartments(List<DepartmentModel> departmentModels);

    List<DepartmentModel> updateDepartments(List<DepartmentModel> departmentModels);

    long deleteDepartments(List<String> ids);
}
