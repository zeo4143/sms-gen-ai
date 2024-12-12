package com.assessment.sms.studentgrades.controller;

import com.assessment.sms.commons.model.FilterRequest;
import com.assessment.sms.studentInfo.request.StudentTGPARequest;
import com.assessment.sms.studentInfo.service.StudentTGPAService;
import com.assessment.sms.studentgrades.model.TGPAModel;
import com.assessment.sms.studentgrades.model.TGPAResponse;
import com.assessment.sms.studentgrades.request.CalculateTGPAAndUpdateCGPARequest;
import com.assessment.sms.studentgrades.request.TGPAListRequest;
import com.assessment.sms.studentgrades.model.TGPAListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grades")
public class TGPAController {

    @Autowired
    private StudentTGPAService studentTGPAService;

    @GetMapping("/{regNo}/tgpa")
    public List<TGPAResponse> getStudentTGPAByParam(@PathVariable String regNo) {
        return studentTGPAService.getStudentTGPAs(regNo);
    }

    @PostMapping("/tgpa/list")
    public List<TGPAListResponse> getStudentTGPAList(
            @RequestBody @Valid TGPAListRequest tGPAListRequest) {

        return studentTGPAService.getStudentsTGPABySemester(
                tGPAListRequest.getRegNos(),
                tGPAListRequest.getSemester(),
                new FilterRequest()
        );
    }


    @PostMapping("/tgpa/calculate")
    public long calculateTGPAAndUpdateCGPA(@RequestBody CalculateTGPAAndUpdateCGPARequest calculateTGPAAndUpdateCGPARequest) {
        return studentTGPAService.calculateTGPAAndUpdateCGPA(
                calculateTGPAAndUpdateCGPARequest.getDeptCodes(),
                calculateTGPAAndUpdateCGPARequest.getSemesters()
        );
    }

}
