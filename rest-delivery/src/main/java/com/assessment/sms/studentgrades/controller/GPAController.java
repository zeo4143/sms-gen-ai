package com.assessment.sms.studentgrades.controller;

import com.assessment.sms.studentgrades.model.GPAModel;
import com.assessment.sms.studentgrades.model.GPAResult;
import com.assessment.sms.studentgrades.request.GPAUpdateRequest;
import com.assessment.sms.studentgrades.service.GPAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
public class GPAController {

    @Autowired
    private GPAService gpaService;

    @GetMapping("/{regNo}/{semester}/gpa")
    public List<GPAResult> getGPA(
            @PathVariable String regNo,
            @PathVariable int semester
    ) {
        return gpaService.getGPAByRegNo(regNo, semester);
    }

    @PutMapping("/gpa")
    public List<GPAModel> updateGPAByReNos(
            @RequestBody List<GPAUpdateRequest> requests) {
//        return gpaService.updateGPAByRegNos(requests);
        return List.of();
    }
}
