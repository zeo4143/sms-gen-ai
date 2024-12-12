package com.assessment.sms.studentgrades.repo;

import com.assessment.sms.commons.model.FilterRequest;
import com.assessment.sms.studentgrades.model.TGPAListResponse;
import com.assessment.sms.studentgrades.model.TGPAResponse;

import java.util.List;

public interface TGPARepo {

    List<TGPAResponse> getStudentTGPAs(String regNo);

    List<TGPAListResponse> getStudentsTGPABySemester(List<String> regNo, int semester, FilterRequest request);

    long calculateTGPAAndUpdateCGPA(List<String> deptCodes, List<Integer> semester);


}
