package com.assessment.sms.studentInfo.mongo.mapper;

import com.assessment.sms.ParentMapper;
import com.assessment.sms.studentInfo.model.StudentPersonalRecordsModel;
import com.assessment.sms.studentInfo.model.StudentPersonalRecordsRequest;
import com.assessment.sms.studentInfo.mongo.entity.StudentInfoEntity;
import com.assessment.sms.studentInfo.mongo.entity.StudentPersonalRecordsRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class StudentPersonalRecordsRepoMapper extends ParentMapper {

    public abstract StudentPersonalRecordsModel toModel(StudentInfoEntity studentInfoEntity);
//
    public abstract List<StudentPersonalRecordsModel> toModel(List<StudentInfoEntity> studentInfoEntities);

    public abstract StudentPersonalRecordsRequestEntity toEntity(StudentPersonalRecordsRequest studentPersonalRecordsRequest);

    public abstract List<StudentPersonalRecordsRequestEntity> toEntity(List<StudentPersonalRecordsRequest> studentPersonalRecordsRequests);

}
