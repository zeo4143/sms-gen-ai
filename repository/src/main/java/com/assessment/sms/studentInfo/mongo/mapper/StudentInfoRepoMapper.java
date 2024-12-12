package com.assessment.sms.studentInfo.mongo.mapper;

import com.assessment.sms.ParentMapper;
import com.assessment.sms.studentInfo.model.StudentInfoModel;
import com.assessment.sms.studentInfo.mongo.entity.StudentInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class StudentInfoRepoMapper extends ParentMapper {

    public abstract StudentInfoModel toModel(StudentInfoEntity studentInfoEntity);

    public abstract List<StudentInfoModel> toModel(List<StudentInfoEntity> studentInfoEntities);

    public abstract StudentInfoEntity toEntity(StudentInfoModel studentInfoModel);

    public abstract List<StudentInfoEntity> toEntity(List<StudentInfoModel> studentInfoModels);
}
