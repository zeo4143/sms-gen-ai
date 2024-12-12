package com.assessment.sms.studentInfo.controller;

import com.assessment.sms.studentInfo.model.StudentInfoModel;
import com.assessment.sms.studentInfo.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentInfoController {

    @Autowired
    private StudentInfoService studentInfoService;

    @GetMapping
    public List<StudentInfoModel> getAllStudentsInfo(@RequestParam(required = false) Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            return studentInfoService.getStudentsInfoByParam(params);
        }
        return studentInfoService.getAllStudentInfos();
    }


    @GetMapping("/{regNo}")
    public StudentInfoModel getStudentInfoById( @PathVariable String regNo) {
        return studentInfoService.getStudentInfoByRegNo(regNo);
    }

    @PostMapping
    public List<StudentInfoModel> addStudentInfo(@RequestBody List<StudentInfoModel> studentInfoModelList) {
        return studentInfoService.createStudentInfo(studentInfoModelList);
    }

    @PutMapping
    public List<StudentInfoModel> updateStudentInfo(@RequestBody List<StudentInfoModel> studentInfoModelList) {
        return studentInfoService.updateStudentInfo(studentInfoModelList);
    }

    @DeleteMapping("/{id}")
    public void deleteStudentInfo(@PathVariable String id) {
        studentInfoService.deleteStudentInfo(id);
    }
}
