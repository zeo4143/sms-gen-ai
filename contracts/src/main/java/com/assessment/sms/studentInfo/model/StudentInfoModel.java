package com.assessment.sms.studentInfo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentInfoModel {
    private String id;
    private String regNo;
    private String firstName;
    private String lastName;
    private EnrollmentStatus enrollmentStatus;
    private StudentAcademicRecordsModel academicRecords;
    private StudentPersonalRecordsModel personalRecords;
}
