package com.assessment.sms.academics.service;

import com.assessment.sms.commons.model.PaginationRequest;
import com.assessment.sms.studentacademics.model.CourseModel;
import com.assessment.sms.studentacademics.repo.CoursesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("coursesService")
public class CoursesService {

    @Autowired
    private CoursesRepo coursesRepo;

    public List<CourseModel> getAllCourses(PaginationRequest paginationRequest) {
       return coursesRepo.getAllCourses(paginationRequest);
    }

    public CourseModel getCourseByCode(String courseCode) {
        return coursesRepo.getCoursesByCode(courseCode);
    }

    public List<CourseModel>  createCourses(List<CourseModel> courses) {
        return coursesRepo.createCourse(courses);
    }

    public List<CourseModel> updateCourses(List<CourseModel> courses) {
        return coursesRepo.updateCourses(courses);
    }

    public long deleteCourses(List<String> courseCodes) {
        return coursesRepo.deleteCourse(courseCodes);
    }
}
