package com.assessment.sms.studentInfo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SemesterModel {
    private int semester;
    private int year;
    private String section;
    private List<String> courseCodes;
}
