package com.assessment.sms.studentInfo.mongo.mapper;

import com.assessment.sms.ParentMapper;
import com.assessment.sms.studentInfo.model.StudentAcademicRecordsModel;
import com.assessment.sms.studentInfo.model.StudentAcademicRecordsRequest;
import com.assessment.sms.studentInfo.mongo.entity.StudentAcademicRecordsRequestEntity;
import com.assessment.sms.studentInfo.mongo.entity.StudentInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class StudentAcademicRecordsRepoMapper extends ParentMapper {

    public abstract StudentAcademicRecordsModel toModel(StudentInfoEntity studentInfoEntity);

    public abstract List<StudentAcademicRecordsModel> toModel(List<StudentInfoEntity> studentInfoEntities);


    public abstract StudentAcademicRecordsRequestEntity toEntity(StudentAcademicRecordsRequest studentAcademicRecordsRequest);

    public abstract List<StudentAcademicRecordsRequest> toEntity(List<StudentAcademicRecordsRequest> studentAcademicRecordsRequests);

}
