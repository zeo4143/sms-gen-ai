package com.assessment.sms.studentgrades.service;

import com.assessment.sms.studentgrades.model.CGPAModel;
import com.assessment.sms.studentgrades.mongo.entity.CGPAEntity;
import com.assessment.sms.studentgrades.mongo.mapper.CGPARepoMapper;
import com.assessment.sms.studentgrades.repo.CGPARepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CGPAService")
public class CGPAService {

    @Autowired
    private CGPARepo cgpaRepo;

    public long updateOrCreateCGPA(List<CGPAModel> cgpas) {
        return cgpaRepo.updateOrCreateCgpa(cgpas);
    }
}
