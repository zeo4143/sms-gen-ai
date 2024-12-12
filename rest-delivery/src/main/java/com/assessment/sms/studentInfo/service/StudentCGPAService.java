package com.assessment.sms.studentInfo.service;

import com.assessment.sms.studentgrades.model.CGPAModel;
import com.assessment.sms.studentgrades.mongo.repo.CGPARepoImpl;
import com.assessment.sms.studentgrades.repo.CGPARepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("studentGradesService")
public class StudentCGPAService {

    @Autowired
    private CGPARepoImpl studentCGPARepo;

    public CGPAModel getStudentCGPAByRegNo(String regNo) {
        return studentCGPARepo.getStudentCGPAByRegNo(regNo);
    }

    public List<CGPAModel> getStudentsCGPAByRegNo(List<String> regNoList) {
        return studentCGPARepo.getStudentsCGPAByRegNo(regNoList);
    }

    public List<CGPAModel> getAllStudentCGPAByParam(Map<String, String> params) {
        return studentCGPARepo.getStudentsCgpaByParam(params);
    }

    public long createStudentCGPA(List<CGPAModel> studentCGPAModelList) {
        return studentCGPARepo.updateOrCreateCgpa(studentCGPAModelList);
    }

    public List<CGPAModel> updateStudentCGPA(List<CGPAModel> studentCGPAModelList) {
        return studentCGPARepo.updateStudentsCGPA(studentCGPAModelList);
    }

    public long deleteStudentCGPAById(List<String> ids) {
        return studentCGPARepo.deleteStudentCGPAById(ids);
    }
}
