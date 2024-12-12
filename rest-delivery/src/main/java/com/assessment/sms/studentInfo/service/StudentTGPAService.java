package com.assessment.sms.studentInfo.service;

import com.assessment.sms.commons.model.FilterRequest;
import com.assessment.sms.studentInfo.request.StudentTGPARequest;
import com.assessment.sms.studentgrades.model.TGPAModel;
import com.assessment.sms.studentgrades.model.TGPAResponse;
import com.assessment.sms.studentgrades.repo.TGPARepo;
import com.assessment.sms.studentgrades.model.TGPAListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("studentTGPAService")
public class StudentTGPAService {

    @Autowired
    private TGPARepo tGPARepo;

    public List<TGPAResponse> getStudentTGPAs(String regNo) {
        return tGPARepo.getStudentTGPAs(regNo);
    }

    public List<TGPAListResponse> getStudentsTGPABySemester(List<String> regNos, int semester, FilterRequest request) {
        return tGPARepo.getStudentsTGPABySemester(regNos, semester,  request);
    }


    public long calculateTGPAAndUpdateCGPA(List<String> deptCodes, List<Integer> semesters) {
       return tGPARepo.calculateTGPAAndUpdateCGPA(deptCodes, semesters);
    }

    public List<TGPAModel> createStudentTGPA(List<StudentTGPARequest> studentTGPARequests) {
        return null;
    }

    public List<TGPAModel> updateStudentTGPA(List<StudentTGPARequest> studentTGPARequests) {
        return null;
    }

    public long deleteStudentsTGPA(List<String> studentTGPARequests) {
        return 0;
    }
}
