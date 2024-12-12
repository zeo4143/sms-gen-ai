package com.assessment.sms.studentgrades.repo;

import com.assessment.sms.studentgrades.model.GPAModel;
import com.assessment.sms.studentgrades.model.GPAResult;

import java.util.List;

public interface GPARepo {

    List<GPAResult> getGPAByRegNo(String regNo, int semester);

    GPAModel updateGPAByRegNos(String regNo, int semester, GPAModel gpaModel);

}
