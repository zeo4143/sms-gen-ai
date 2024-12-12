package com.assessment.sms.studentgrades.controller;

import com.assessment.sms.studentgrades.model.ExamResultsModel;
import com.assessment.sms.studentgrades.service.ExamResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
public class ExamResultsController {

    @Autowired
    private ExamResultsService examResultsService;

    @PostMapping("/exams/")
    public long insertExamResults(@RequestBody List<ExamResultsModel> examResults) {
        return examResultsService.insertExamResults(examResults);
    }

}
