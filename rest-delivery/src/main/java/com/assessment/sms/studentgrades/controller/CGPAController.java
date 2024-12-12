package com.assessment.sms.studentgrades.controller;

import com.assessment.sms.studentInfo.service.StudentCGPAService;
import com.assessment.sms.studentgrades.model.CGPAModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/grades")
public class CGPAController {

    @Autowired
    private StudentCGPAService studentCGPAService;


    @GetMapping("/cgpa")
    public List<CGPAModel> getStudentCGPAByParam(@RequestParam(required = false) Map<String, String> params) {
        return studentCGPAService.getAllStudentCGPAByParam(params);
    }

    @GetMapping("/cgpa/list")
    public List<CGPAModel> getStudentsCGPAByRegNos(@RequestParam List<String> regNos) {
        return studentCGPAService.getStudentsCGPAByRegNo(regNos);
    }

    @GetMapping("/{regNo}/cgpa")
    public CGPAModel getStudentCGPAByRegNo(@PathVariable String regNo) {
        return studentCGPAService.getStudentCGPAByRegNo(regNo);
    }

}
