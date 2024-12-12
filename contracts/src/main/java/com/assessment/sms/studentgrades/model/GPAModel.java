package com.assessment.sms.studentgrades.model;

import lombok.Getter;
import lombok.Setter;

import static com.assessment.sms.studentgrades.model.ExamResultStatus.otherGrades;

@Getter
@Setter
public class GPAModel {
    private String courseCode;
    private ExamResultStatus examResultStatus;
    private Integer marksObtained;
    private Integer maxMarks;
    private Integer gpa;
    private String grade;
    private String gradeDescription;
}
