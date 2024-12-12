package com.assessment.sms.studentInfo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentAcademicRecordsRequest {
    private String id;
    private StudentAcademicRecordsModel academicRecords;
}
