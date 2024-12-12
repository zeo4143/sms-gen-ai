package com.assessment.sms.studentgrades.request;

import com.assessment.sms.studentgrades.model.GPAModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GPAUpdateRequest {
    private String regNo;
    private int semester;
    private GPAModel gpaModel;
}
