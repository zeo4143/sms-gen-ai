package com.assessment.sms.studentgrades.repo;

import com.assessment.sms.studentgrades.model.ExamResultsModel;

import java.util.List;

public interface ExamResultsRepo {

    long insertExamResults(List<ExamResultsModel> examResults);
}
