package com.assessment.sms.studentgrades.mongo.mapper;

import com.assessment.sms.ParentMapper;
import com.assessment.sms.studentgrades.model.GPAModel;
import com.assessment.sms.studentgrades.mongo.entity.GPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class GPARepoMapper extends ParentMapper {

    public abstract GPAEntity toEntity(GPAModel gpaModel);

    public abstract GPAModel toModel(GPAEntity gpaEntity);

    public abstract List<GPAModel> toModel(List<GPAEntity> gpaEntities);

    public abstract List<GPAEntity> toEntity(List<GPAModel> gpaModels);
}

