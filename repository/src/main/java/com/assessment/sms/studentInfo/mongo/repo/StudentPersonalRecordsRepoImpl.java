package com.assessment.sms.studentInfo.mongo.repo;

import com.assessment.sms.commons.mongo.utility.MongoUtility;
import com.assessment.sms.commons.usecases.UtilitiesUseCase;
import com.assessment.sms.studentInfo.model.StudentPersonalRecordsModel;
import com.assessment.sms.studentInfo.model.StudentPersonalRecordsRequest;
import com.assessment.sms.studentInfo.repo.StudentPersonalRecordsRepo;
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

@Repository("studentPersonalRecordsRepoImpl")
public class StudentPersonalRecordsRepoImpl implements StudentPersonalRecordsRepo {

    private static final Class<StudentPersonalRecordsModel> clazz = StudentPersonalRecordsModel.class;
    private static final String ACADEMIC_RECORDS = "academicRecords";

    @Autowired
    private MongoUtility mongoUtility;

    @Autowired
    private UtilitiesUseCase utilitiesUseCase;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("students")
    private String collection;

    @Override
    public StudentPersonalRecordsModel getSingleStudentPersonalRecordsById(String studentId) {
        Query query = new Query(Criteria.where(ID).is(new ObjectId(studentId)));
        query.fields().include(ACADEMIC_RECORDS).exclude(ID);
        return mongoTemplate.findOne(query, clazz, collection);
    }

    @Override
    public List<StudentPersonalRecordsModel> studentPersonalRecordsByStudentById(List<String> studentIds) {
        Query query = new Query(Criteria.where(ID).in(mongoUtility.createObjectIdFromString(studentIds)));
        query.fields().include(ACADEMIC_RECORDS).exclude(ID);
        return mongoTemplate.find(query, clazz, collection);
    }

    @Override
    public List<StudentPersonalRecordsModel> studentPersonalRecordsByParam(List<String> studentIds, Map<String, String> params) {
        Query query = new Query(Criteria.where(ID).in(mongoUtility.createObjectIdFromString(studentIds)));

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = ACADEMIC_RECORDS + "." + utilitiesUseCase.extractParamFromKey(entry.getKey());
            query.addCriteria(Criteria.where(key).is(entry.getValue()));
        }

        query.fields().include(ACADEMIC_RECORDS).exclude(ID);
        return mongoTemplate.find(query, clazz, collection);
    }

    @Override
    public List<StudentPersonalRecordsModel> updateStudentPersonalRecords(
            List<StudentPersonalRecordsRequest> studentPersonalRecordsRequests) {
        return List.of();
//        List<StudentPersonalRecordsModel>
//        for (StudentPersonalRecordsRequest studentPersonalRecordsRequest : studentPersonalRecordsRequests) {
//            Query query = new Query(Criteria.where(ID).is(new ObjectId(studentPersonalRecordsRequest.getId())));
//            query.fields().include(ACADEMIC_RECORDS).exclude(ID);
//
//        }
    }
}
