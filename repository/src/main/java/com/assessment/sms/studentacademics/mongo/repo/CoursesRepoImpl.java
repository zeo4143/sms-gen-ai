package com.assessment.sms.studentacademics.mongo.repo;

import com.assessment.sms.commons.model.PaginationRequest;
import com.assessment.sms.commons.mongo.utility.MongoFilterUtility;
import com.assessment.sms.commons.mongo.utility.MongoUtility;
import com.assessment.sms.studentacademics.model.CourseModel;
import com.assessment.sms.studentacademics.mongo.entity.CoursesEntity;
import com.assessment.sms.studentacademics.mongo.mapper.CoursesRepoMapper;
import com.assessment.sms.studentacademics.repo.CoursesRepo;
import com.mongodb.bulk.BulkWriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.assessment.sms.commons.mongo.utility.MongoFieldConstants.ID;

@Repository("coursesRepoImpl")
public class CoursesRepoImpl implements CoursesRepo {

    private static final Class<CourseModel> clazz = CourseModel.class;
    private static final String COURSE_CODE = "courseCode";

    @Autowired
    private MongoUtility mongoUtility;

    @Autowired
    private CoursesRepoMapper mapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("courses")
    private String collection;

    @Override
    public List<CourseModel> getAllCourses(PaginationRequest paginationRequest) {
        Query query = new Query();
        MongoFilterUtility.addPagination(paginationRequest, query);
        query.with(Sort.by(Sort.Direction.ASC, COURSE_CODE));
        return mongoTemplate.find(query, clazz, collection);
    }

    @Override
    public CourseModel getCoursesByCode(String courseCode) {
        return mongoTemplate.findOne(
                Query.query(Criteria.where(COURSE_CODE).is(courseCode)),
                clazz,
                collection
        );
    }

    @Override
    public List<CourseModel> createCourse(List<CourseModel> courseModel) {
        List<CoursesEntity> coursesEntities = mapper.toEntity(courseModel);
        mongoTemplate.insert(coursesEntities, collection);
        return mapper.toModel(coursesEntities);
    }

    @Override
    public List<CourseModel> updateCourses(List<CourseModel> courseModels) {

        List<CoursesEntity> coursesEntities = mapper.toEntity(courseModels);

        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, clazz, collection);

        for (CoursesEntity coursesEntity : coursesEntities) {
            bulkOps.updateOne(
                    Query.query(Criteria.where(COURSE_CODE).is(coursesEntity.getCourseCode())),
                   mongoUtility.updateMapper(coursesEntity, "")
            );
        }

        BulkWriteResult bulkWriteResult =  bulkOps.execute();

        if (bulkWriteResult.getModifiedCount() == coursesEntities.size()) {
            return mapper.toModel(coursesEntities);
        }

        return null;
    }

    @Override
    public long deleteCourse(List<String> courseCodes) {
        return mongoTemplate.remove(
                Query.query(Criteria.where(COURSE_CODE).in(courseCodes)),
                clazz,
                collection
        ).getDeletedCount();
    }
}
