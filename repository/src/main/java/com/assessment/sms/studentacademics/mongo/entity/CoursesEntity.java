package com.assessment.sms.studentacademics.mongo.entity;

import com.assessment.sms.studentacademics.model.DepartmentAndCourseStatus;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
public class CoursesEntity {
    private ObjectId id;
    private String courseCode;
    private String courseName;
    private int courseCredits;
    private String coursePlanLink;
    private DepartmentAndCourseStatus courseStatus;
}
