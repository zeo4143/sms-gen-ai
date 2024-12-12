package com.assessment.sms.studentInfo.mongo.entity;

import com.assessment.sms.studentInfo.model.EnrollmentStatus;
import com.assessment.sms.studentInfo.model.StudentAcademicRecordsModel;
import com.assessment.sms.studentInfo.model.StudentPersonalRecordsModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
public class StudentInfoEntity {
    private ObjectId id;
    private String regNo;
    private String firstName;
    private String lastName;
    private String batch;
    private EnrollmentStatus enrollmentStatus;
    private StudentAcademicRecordsModel academicRecords;
    private StudentPersonalRecordsModel personalRecords;
}
