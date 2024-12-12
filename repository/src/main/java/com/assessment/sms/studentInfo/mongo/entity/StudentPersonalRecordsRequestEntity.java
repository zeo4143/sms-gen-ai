package com.assessment.sms.studentInfo.mongo.entity;

import com.assessment.sms.studentInfo.model.StudentPersonalRecordsModel;
import org.bson.types.ObjectId;

public class StudentPersonalRecordsRequestEntity {
    private ObjectId id;
    private StudentPersonalRecordsModel personalRecords;
}
