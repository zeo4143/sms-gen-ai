package com.assessment.sms.studentacademics.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DepartmentModel {
    private String id;
    private String departmentName;
    private String departmentCode;
    private DepartmentTypes departmentType;
    private DepartmentAndCourseStatus departmentAndCourseStatus;
    private List<String> courseCodes;
}
