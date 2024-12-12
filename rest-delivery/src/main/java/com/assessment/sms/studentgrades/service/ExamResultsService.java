package com.assessment.sms.studentgrades.service;

import com.assessment.sms.studentgrades.model.ExamResultsModel;
import com.assessment.sms.studentgrades.repo.ExamResultsRepo;
import com.assessment.sms.studentgrades.repo.GPARepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("examResultsService")
public class ExamResultsService {

    @Autowired
    private ExamResultsRepo examResultsRepo;

    public long insertExamResults(List<ExamResultsModel> examResults) {
        return examResultsRepo.insertExamResults(examResults);
    }
}
