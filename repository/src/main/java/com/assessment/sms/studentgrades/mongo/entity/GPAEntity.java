package com.assessment.sms.studentgrades.mongo.entity;

import com.assessment.sms.studentgrades.model.ExamResultStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GPAEntity {
    private String courseCode;
    private ExamResultStatus examResultStatus;
    private Integer marksObtained;
    private Integer maxMarks;
    private Integer gpa;
    private String grade;
    private String gradeDescription;
}