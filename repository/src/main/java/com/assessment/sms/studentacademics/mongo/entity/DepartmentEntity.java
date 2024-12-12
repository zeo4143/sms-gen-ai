package com.assessment.sms.studentacademics.mongo.entity;

import com.assessment.sms.studentacademics.model.DepartmentAndCourseStatus;
import com.assessment.sms.studentacademics.model.DepartmentTypes;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
public class DepartmentEntity {
    private ObjectId id;
    private String departmentName;
    private String departmentCode;
    private DepartmentTypes departmentType;
    private DepartmentAndCourseStatus departmentAndCourseStatus;
    private List<String> courseCodes;
}
