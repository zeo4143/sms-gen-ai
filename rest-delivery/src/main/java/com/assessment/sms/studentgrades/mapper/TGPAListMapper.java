package com.assessment.sms.studentgrades.mapper;

import com.assessment.sms.studentgrades.model.CGPAModel;
import com.assessment.sms.studentgrades.model.TGPAListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public abstract class TGPAListMapper {
//    public abstract TGPAListResponse toResponse(CGPAModel cgpaModel);
}
