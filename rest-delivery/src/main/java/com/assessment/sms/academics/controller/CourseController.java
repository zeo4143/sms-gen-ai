package com.assessment.sms.academics.controller;

import com.assessment.sms.academics.service.CoursesService;
import com.assessment.sms.commons.model.PaginationRequest;
import com.assessment.sms.studentacademics.model.CourseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CoursesService coursesService;

    @GetMapping
    public List<CourseModel> getAllCourses(
            @RequestParam Integer pageNo,
            @RequestParam Integer pageSize
    ) {
        PaginationRequest paginationRequest = new PaginationRequest(pageNo, pageSize);
        return coursesService.getAllCourses(paginationRequest);
    }

    @GetMapping("/{courseCode}")
    public CourseModel getCourseByCode(@PathVariable String courseCode) {
        return coursesService.getCourseByCode(courseCode);
    }

    @PostMapping
    public List<CourseModel> createCourse(@RequestBody List<CourseModel> courses) {
        return coursesService.createCourses(courses);
    }

    @PutMapping
    public List<CourseModel> updateCourse(@RequestBody List<CourseModel> courses) {
        return coursesService.updateCourses(courses);
    }

    @DeleteMapping
    public long deleteCourse(@RequestBody List<String> courseCodes) {
        return coursesService.deleteCourses(courseCodes);
    }

}
