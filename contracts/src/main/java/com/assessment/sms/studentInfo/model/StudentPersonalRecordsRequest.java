package com.assessment.sms.studentInfo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentPersonalRecordsRequest {
    private String id;
    private StudentPersonalRecordsModel studentPersonalRecordsModel;
}
