package com.assessment.sms.studentgrades.mongo.entity;

import com.assessment.sms.studentgrades.model.TGPAModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
public class CGPAEntity {
    private ObjectId id;
    private String regNo;
    private Float cgpa;
    private List<TGPAModel> tgpa;
}
