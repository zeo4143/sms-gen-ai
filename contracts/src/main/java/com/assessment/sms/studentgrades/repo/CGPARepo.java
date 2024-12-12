package com.assessment.sms.studentgrades.repo;

import com.assessment.sms.studentgrades.model.CGPAModel;

import java.util.List;
import java.util.Map;

public interface CGPARepo {

    CGPAModel getStudentCGPAByRegNo(String regNo);

    List<CGPAModel> getStudentsCGPAByRegNo(List<String> regNoList);

    List<CGPAModel> getStudentsCgpaByParam(Map<String, String> params);

    long updateOrCreateCgpa(List<CGPAModel> CGPAList);

    List<CGPAModel> updateStudentsCGPA(List<CGPAModel> studentCGPAModelList);

    long deleteStudentCGPAById(List<String> studentIds);
}
