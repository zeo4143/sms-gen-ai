package com.assessment.sms.studentacademics.mongo.mapper;

import com.assessment.sms.ParentMapper;
import com.assessment.sms.studentacademics.model.DepartmentModel;
import com.assessment.sms.studentacademics.mongo.entity.DepartmentEntity;
import com.assessment.sms.studentacademics.repo.DepartmentRepo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DepartmentRepoMapper extends ParentMapper {

    public abstract DepartmentModel toModel(DepartmentEntity departmentEntity);

    public abstract DepartmentEntity toEntity(DepartmentModel departmentModel);

    public abstract List<DepartmentModel> toModel(List<DepartmentEntity> departmentEntities);

    public abstract List<DepartmentEntity> toEntity(List<DepartmentModel> departmentModels);
}
