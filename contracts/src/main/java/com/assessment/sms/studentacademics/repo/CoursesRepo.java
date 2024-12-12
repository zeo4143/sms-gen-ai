package com.assessment.sms.studentacademics.repo;

import com.assessment.sms.commons.model.PaginationRequest;
import com.assessment.sms.studentacademics.model.CourseModel;

import java.util.List;

public interface CoursesRepo {

    List<CourseModel> getAllCourses(PaginationRequest paginationRequest);

    CourseModel getCoursesByCode(String courseId);

    List<CourseModel> createCourse(List<CourseModel> courseModel);

    List<CourseModel> updateCourses(List<CourseModel> courseModel);

    long deleteCourse(List<String> courseCodes);

}
