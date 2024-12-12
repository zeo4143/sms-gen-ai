package com.assessment.sms.studentInfo.request;

import com.assessment.sms.commons.model.FilterRequest;
import com.assessment.sms.studentgrades.model.TGPAModel;

public class StudentTGPARequest {
    private String studentId;
    private TGPAModel TGPAModel;
    private FilterRequest filterRequest;
}
