package com.assessment.sms.studentInfo.mongo.entity;

import com.assessment.sms.studentInfo.model.StudentAcademicRecordsModel;
import org.bson.types.ObjectId;

public class StudentAcademicRecordsRequestEntity {
    private ObjectId id;
    private StudentAcademicRecordsModel academicRecords;
}
