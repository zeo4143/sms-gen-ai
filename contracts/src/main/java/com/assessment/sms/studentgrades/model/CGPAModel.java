package com.assessment.sms.studentgrades.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CGPAModel {
    private String id;
    private String regNo;
    private Float cgpa;
    private List<TGPAModel> tgpa;
}
