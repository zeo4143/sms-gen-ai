package com.assessment.sms.studentgrades.mongo.mapper;

import com.assessment.sms.ParentMapper;
import com.assessment.sms.studentgrades.model.ExamResultsModel;
import com.assessment.sms.studentgrades.model.GPAModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ExamResultsGPAMapper  extends ParentMapper {

    public abstract GPAModel toModel(ExamResultsModel examResultsModel);
}
