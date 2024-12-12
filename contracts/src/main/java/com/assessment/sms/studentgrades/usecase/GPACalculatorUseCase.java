package com.assessment.sms.studentgrades.usecase;

import com.assessment.sms.studentgrades.model.CGPAModel;
import com.assessment.sms.studentgrades.model.GPAModel;
import com.assessment.sms.studentgrades.model.TGPAModel;

import java.util.List;

public interface GPACalculatorUseCase {

    GPAModel calculateGPA(GPAModel gpa);

    List<GPAModel> calculateGPA(List<GPAModel> gpaList);

    TGPAModel calculateTGPA(TGPAModel tgpa);

    List<TGPAModel> calculateTGPA(List<TGPAModel> tgpaList);

    CGPAModel calculateCGPA(CGPAModel cgpa);

    List<CGPAModel> calculateCGPA(List<CGPAModel> cgpaList);
}
