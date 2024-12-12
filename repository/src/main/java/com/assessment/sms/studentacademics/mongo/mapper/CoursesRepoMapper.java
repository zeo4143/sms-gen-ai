package com.assessment.sms.studentacademics.mongo.mapper;

import com.assessment.sms.ParentMapper;
import com.assessment.sms.studentacademics.model.CourseModel;
import com.assessment.sms.studentacademics.mongo.entity.CoursesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CoursesRepoMapper extends ParentMapper {

    public abstract CourseModel toModel(CoursesEntity coursesEntity);

    public abstract CoursesEntity toEntity(CourseModel courseModel);

    public abstract List<CourseModel> toModel(List<CoursesEntity> coursesEntities);

    public abstract List<CoursesEntity> toEntity(List<CourseModel> courseModels);

}
