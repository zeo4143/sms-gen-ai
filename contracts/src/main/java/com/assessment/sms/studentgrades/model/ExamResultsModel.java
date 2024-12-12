package com.assessment.sms.studentgrades.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamResultsModel {
    private String regNo;
    private String courseCode;
    private int semester;
    private ExamResultStatus examResultStatus;
    private int marksObtained;
    private int maxMarks;
}
