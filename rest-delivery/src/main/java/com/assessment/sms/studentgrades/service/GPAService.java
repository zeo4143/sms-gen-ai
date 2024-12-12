package com.assessment.sms.studentgrades.service;

import com.assessment.sms.studentgrades.model.GPAModel;
import com.assessment.sms.studentgrades.model.GPAResult;
import com.assessment.sms.studentgrades.repo.GPARepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("gpaService")
public class GPAService {

    @Autowired
    private GPARepo gpaRepo;

    public GPAModel updateGPAByRegNos(String regNo, int semester, GPAModel gpaModel) {
        return gpaRepo.updateGPAByRegNos(regNo, semester, gpaModel);
    }

    public List<GPAResult> getGPAByRegNo(String regNo, int semester) {
        return gpaRepo.getGPAByRegNo(regNo, semester);
    }
}
