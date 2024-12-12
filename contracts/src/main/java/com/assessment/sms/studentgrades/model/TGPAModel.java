package com.assessment.sms.studentgrades.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Getter
@Setter
public class TGPAModel {
    @NotBlank
    private int semester;
    private Float tgpa;
    private List<GPAModel> gpa;
}
