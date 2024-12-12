package com.assessment.sms.studentgrades.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CalculateTGPAAndUpdateCGPARequest {
    private List<String> deptCodes;
    private List<Integer> semesters;
}
