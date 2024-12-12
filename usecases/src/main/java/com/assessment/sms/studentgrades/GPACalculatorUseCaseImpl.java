package com.assessment.sms.studentgrades;

import com.assessment.sms.studentgrades.model.ExamResultStatus;
import com.assessment.sms.studentgrades.model.CGPAModel;
import com.assessment.sms.studentgrades.model.GPAModel;
import com.assessment.sms.studentgrades.model.TGPAModel;
import com.assessment.sms.studentgrades.usecase.GPACalculatorUseCase;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("GPACalculatorUseCaseImpl")
public class GPACalculatorUseCaseImpl implements GPACalculatorUseCase {

    @Override
    public GPAModel calculateGPA(GPAModel gpa) {
        ExamResultStatus examResultStatus = gpa.getExamResultStatus();
        switch(examResultStatus) {
            case ABSENT:
                gpa.setGrade("G");
                gpa.setGpa(0);
                gpa.setGradeDescription("Absent");
                break;
            case FAIL:
                gpa.setGrade("F");
                gpa.setGradeDescription("Fail");
                gpa.setGpa(0);
                break;
            case PASS:
                int marksObtained = gpa.getMarksObtained();
                gpa.setGpa((marksObtained/10) + 1);
                if (marksObtained >= 91 && marksObtained <= 100) {
                    gpa.setGrade("O");
                    gpa.setGradeDescription("Outstanding");
                } else if (marksObtained >= 81 && marksObtained <= 90) {
                    gpa.setGrade("A+");
                    gpa.setGradeDescription("Excellent");
                } else if (marksObtained >= 71 && marksObtained <= 80) {
                    gpa.setGrade("A");
                    gpa.setGradeDescription("Very Good");
                } else if (marksObtained >= 61 && marksObtained <= 70) {
                    gpa.setGrade("B+");
                    gpa.setGradeDescription("Good");
                } else if (marksObtained >= 51 && marksObtained <= 60) {
                    gpa.setGrade("B");
                    gpa.setGradeDescription("Average");
                } else if (marksObtained >= 41 && marksObtained <= 50) {
                    gpa.setGrade("C");
                    gpa.setGradeDescription("Below Average");
                } else if (marksObtained >= 35 && marksObtained <= 40) {
                    gpa.setGrade("D");
                    gpa.setGradeDescription("Pass");
                } else if (marksObtained >= 0 && marksObtained <= 34) {
                    gpa.setGrade("F");
                    gpa.setGradeDescription("Fail");
                }

                break;
        }

        return gpa;
    }

    @Override
    public List<GPAModel> calculateGPA(List<GPAModel> gpaList) {
        return gpaList.stream().map(this::calculateGPA).collect(Collectors.toList());
    }

    @Override
    public TGPAModel calculateTGPA(TGPAModel tgpa) {
        List<GPAModel> gpaList = tgpa.getGpa();

        if (gpaList == null || gpaList.isEmpty()) {
            tgpa.setTgpa(0.00f);
            return tgpa;
        }

//
        int tGPA = gpaList.stream()
                .filter(Objects::nonNull)
                .map(GPAModel::getGpa)
                .filter(Objects::nonNull)
                .reduce(0, Integer::sum);

        if (tGPA > 0) {
            tgpa.setTgpa(BigDecimal.valueOf(tGPA / gpaList.size())
                    .setScale(2, RoundingMode.HALF_UP)
                    .floatValue());
        } else {
            tgpa.setTgpa(0.00f);
        }
        return tgpa;
    }

    @Override
    public List<TGPAModel> calculateTGPA(List<TGPAModel> tgpaList) {
        return tgpaList.stream().map(this::calculateTGPA).collect(Collectors.toList());
    }

    @Override
    public CGPAModel calculateCGPA(CGPAModel cgpa) {
        List<TGPAModel> tgpaList = cgpa.getTgpa();
        float cGPA = tgpaList.stream().map(TGPAModel::getTgpa).reduce(0f, Float::sum);
        if (!tgpaList.isEmpty()) {
            cgpa.setCgpa(BigDecimal.valueOf((double) cGPA / tgpaList.size())
                    .setScale(2, RoundingMode.HALF_UP)
                    .floatValue());
        } else {
            cgpa.setCgpa(0.00f);
        }
        return cgpa;
    }

    @Override
    public List<CGPAModel> calculateCGPA(List<CGPAModel> cgpaList) {
        return cgpaList.stream().map(this::calculateCGPA).collect(Collectors.toList());
    }
}
