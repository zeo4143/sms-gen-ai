package com.assessment.sms.studentInfo.mongo.repo;

import com.assessment.sms.commons.mongo.utility.MongoUtility;
import com.assessment.sms.commons.usecases.UtilitiesUseCase;
import com.assessment.sms.studentInfo.model.StudentAcademicRecordsModel;
import com.assessment.sms.studentInfo.model.StudentAcademicRecordsRequest;
import com.assessment.sms.studentInfo.repo.StudentAcademicRecordsRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.assessment.sms.commons.mongo.utility.MongoFieldConstants.ID;

@Repository("studentAcademicRecordsImpl")
public class StudentAcademicRecordsImpl implements StudentAcademicRecordsRepo {

    private static final Class<StudentAcademicRecordsModel> clazz = StudentAcademicRecordsModel.class;
    private static final String PERSONAL_RECORDS = "personalRecords";

    @Autowired
    private UtilitiesUseCase utilitiesUseCase;

    @Autowired
    private MongoUtility mongoUtility;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("students")
    private String collection;

    @Override
    public StudentAcademicRecordsModel getSingleStudentAcademicRecordsByStudentById(String studentId) {
        Query query = new Query(Criteria.where(ID).is(new ObjectId(studentId)));
        query.fields().include(PERSONAL_RECORDS).exclude(ID);
        return mongoTemplate.findOne(query, clazz, collection);
    }

    @Override
    public List<StudentAcademicRecordsModel> getStudentsAcademicRecordsByStudentsById(List<String> studentIds) {
        Query query = new Query(Criteria.where(ID).in(mongoUtility.createObjectIdFromString(studentIds)));
        query.fields().include(PERSONAL_RECORDS).exclude(ID);
        return mongoTemplate.find(query, clazz, collection);
    }

    @Override
    public List<StudentAcademicRecordsModel> getStudentsAcademicRecordsByParam(List<String> studentIds,
            Map<String, String> params) {
        Query query = new Query(Criteria.where(ID).in(mongoUtility.createObjectIdFromString(studentIds)));

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = PERSONAL_RECORDS + "." + utilitiesUseCase.extractParamFromKey(entry.getKey());
            query.addCriteria(Criteria.where(key).is(entry.getValue()));
        }

        query.fields().include(PERSONAL_RECORDS).exclude(ID);
        return mongoTemplate.find(query, clazz, collection);
    }

    @Override
    public List<StudentAcademicRecordsModel> updateStudentsAcademicRecords(
            List<StudentAcademicRecordsRequest> studentAcademicRecordsRequests) {
        return List.of();
    }
}
