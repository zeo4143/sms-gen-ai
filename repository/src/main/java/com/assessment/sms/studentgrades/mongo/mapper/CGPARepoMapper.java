package com.assessment.sms.studentgrades.mongo.mapper;

import com.assessment.sms.ParentMapper;
import com.assessment.sms.studentgrades.model.CGPAModel;
import com.assessment.sms.studentgrades.mongo.entity.CGPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CGPARepoMapper extends ParentMapper {

    public abstract CGPAEntity toEntity(CGPAModel studentCGPAModel);

    public abstract CGPAModel toModel(CGPAEntity studentCGPAEntity);

    public abstract List<CGPAEntity> toEntity(List<CGPAModel> studentCGPAModelList);

    public abstract List<CGPAModel> toModel(List<CGPAEntity> studentCGPAEntityList);

}
