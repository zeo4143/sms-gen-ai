package com.assessment.sms.studentgrades.mongo.repo;

import com.assessment.sms.commons.mongo.utility.AggregationUtility;
import com.assessment.sms.commons.mongo.utility.MongoUtility;
import com.assessment.sms.commons.usecases.UtilitiesUseCase;
import com.assessment.sms.studentgrades.model.CGPAModel;
import com.assessment.sms.studentgrades.mongo.entity.CGPAEntity;
import com.assessment.sms.studentgrades.repo.CGPARepo;
import com.assessment.sms.studentgrades.mongo.mapper.CGPARepoMapper;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static com.assessment.sms.commons.mongo.utility.MongoFieldConstants.ID;

@Repository
public class CGPARepoImpl implements CGPARepo {

    private final Class<CGPAEntity> clazz = CGPAEntity.class;
    private final String REG_NO = "regNo";


    @Autowired
    private CGPARepoMapper mapper;


    @Autowired
    private UtilitiesUseCase utilitiesUseCase;

    @Autowired
    private MongoUtility mongoUtility;

    @Autowired
    private AggregationUtility aggregationUtility;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("grades")
    private String collection;

    @Override
    public CGPAModel getStudentCGPAByRegNo(String regNo) {
        CGPAEntity entity = mongoTemplate.findOne(
                Query.query(Criteria.where(REG_NO).is(regNo)),
                clazz,
                collection
        );
        return mapper.toModel(entity);
    }

    @Override
    public List<CGPAModel> getStudentsCGPAByRegNo(List<String> regNoList) {
        List<CGPAEntity> entities = mongoTemplate.find(
                Query.query(Criteria.where(REG_NO).in(regNoList)),
                clazz,
                collection
        );
        return mapper.toModel(entities);
    }

    @Override
    public List<CGPAModel> getStudentsCgpaByParam(Map<String, String> params) {
        Query query = new Query();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = utilitiesUseCase.extractParamFromKey(entry.getKey());
            query.addCriteria(Criteria.where(key).is(entry.getValue()));
        }

        List<CGPAEntity> entities = mongoTemplate.find(
                query,
                clazz,
                collection
        );
        return mapper.toModel(entities);
    }

    @Override
    public long updateOrCreateCgpa(List<CGPAModel> CGPAList) {
        List<CGPAEntity> entities = mapper.toEntity(CGPAList);

        for (CGPAEntity entity : entities) {
            Query query = new Query(Criteria.where(REG_NO).is(entity.getRegNo()));

        }

        return 0;
    }

    @Override
    public List<CGPAModel> updateStudentsCGPA(List<CGPAModel> studentCGPAModelList) {
        List<CGPAModel> updatedList = new ArrayList<>();
        List<CGPAEntity> studentCGPAEntityList = mapper.toEntity(studentCGPAModelList);
        for (CGPAEntity studentCGPAEntity : studentCGPAEntityList) {
            Query query = new Query(Criteria.where(ID).is(studentCGPAEntity.getId()));
            UpdateResult result = mongoTemplate.updateFirst(query,mongoUtility.updateMapper(studentCGPAEntity, ""), clazz,
                    collection);
            if (result.getMatchedCount() > 0) {
                updatedList.add(mapper.toModel(studentCGPAEntity));
            }
        }

        return updatedList;
    }

    @Override
    public long deleteStudentCGPAById(List<String> studentIds) {
       List<ObjectId> ids = studentIds.stream().map(mapper::stringtoObjectId).collect(Collectors.toList());
      return mongoTemplate.remove(ids, collection).getDeletedCount();
    }

}
