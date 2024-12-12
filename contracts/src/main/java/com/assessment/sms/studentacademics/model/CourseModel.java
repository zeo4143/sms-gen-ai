package com.assessment.sms.studentacademics.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseModel {
    private String id;
    private String courseCode;
    private String courseName;
    private int courseCredits;
    private String coursePlanLink;
    private DepartmentAndCourseStatus courseStatus;
}
