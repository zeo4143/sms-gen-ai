package com.assessment.sms.studentInfo.mongo.repo;

import com.assessment.sms.commons.mongo.utility.MongoUtility;
import com.assessment.sms.studentInfo.model.StudentInfoModel;
import com.assessment.sms.studentInfo.mongo.entity.StudentInfoEntity;
import com.assessment.sms.studentInfo.mongo.mapper.StudentInfoRepoMapper;
import com.assessment.sms.studentInfo.repo.StudentInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.assessment.sms.commons.mongo.utility.MongoFieldConstants.ID;

@Repository("studentInfoRepoImpl")
public class StudentInfoRepoImpl implements StudentInfoRepo {

    private static final Class<StudentInfoEntity> clazz = StudentInfoEntity.class;
    private static final String REG_NO = "regNo";

    @Autowired
    private MongoUtility mongoUtility;

    @Autowired
    private StudentInfoRepoMapper studentInfoRepoMapper;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Value("students")
    private String collection;

    @Override
    public StudentInfoModel getStudentInfoByRegNo(String regNo) {
        StudentInfoEntity studentInfoEntity = mongoTemplate.findOne(
                Query.query(Criteria.where(REG_NO).is(regNo)),
                clazz,
                collection);
        return studentInfoRepoMapper.toModel(studentInfoEntity);
    }

    @Override
    public List<StudentInfoModel> getAllStudentInfo() {
        List<StudentInfoEntity> studentInfoEntities = mongoTemplate.findAll(StudentInfoEntity.class, collection);
        return studentInfoRepoMapper.toModel(studentInfoEntities);
    }

    @Override
    public List<StudentInfoModel> getStudentsInfoByParam(Map<String, Object> params) {
        List<StudentInfoEntity> studentInfoEntities = mongoTemplate.find(mongoUtility.createQueryFromParams(params), clazz,
                collection);
        return studentInfoRepoMapper.toModel(studentInfoEntities);
    }

    @Override
    public List<StudentInfoModel> createStudentsInfo(List<StudentInfoModel> studentInfoModelList) {
        List<StudentInfoEntity> studentInfoEntity = studentInfoRepoMapper.toEntity(studentInfoModelList);
        mongoTemplate.insert(studentInfoEntity, collection);
        return studentInfoRepoMapper.toModel(studentInfoEntity);
    }


    @Override
    public List<StudentInfoModel> updateStudentsInfo(List<StudentInfoModel> studentInfoModelList) {
        List<StudentInfoEntity> studentsInfoEntity = studentInfoRepoMapper.toEntity(studentInfoModelList);
        mongoTemplate.save(studentsInfoEntity, collection);
        return studentInfoRepoMapper.toModel(studentsInfoEntity);
    }

    public void deleteStudentInfo(String studentId) {
        Query query = new Query(Criteria.where(ID).is(studentInfoRepoMapper.stringtoObjectId(studentId)));
        mongoTemplate.remove(query, clazz, collection);
    }
}
