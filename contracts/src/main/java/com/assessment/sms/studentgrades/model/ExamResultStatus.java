package com.assessment.sms.studentgrades.model;

import java.util.Arrays;
import java.util.List;

public enum ExamResultStatus {
    PASS,
    FAIL,
    ABSENT,
    CAREER_EXCHANGE,
    IMPROVEMENT;

    public static List<ExamResultStatus> otherGrades() {
        return Arrays.asList(ABSENT, CAREER_EXCHANGE, IMPROVEMENT);
    }
}
