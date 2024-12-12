package com.assessment.sms.studentacademics.mongo.repo;

import com.assessment.sms.commons.model.PaginationRequest;
import com.assessment.sms.commons.mongo.utility.MongoFilterUtility;
import com.assessment.sms.commons.mongo.utility.MongoUtility;
import com.assessment.sms.studentacademics.model.DepartmentModel;
import com.assessment.sms.studentacademics.mongo.entity.DepartmentEntity;
import com.assessment.sms.studentacademics.mongo.mapper.DepartmentRepoMapper;
import com.assessment.sms.studentacademics.repo.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.assessment.sms.commons.mongo.utility.MongoFieldConstants.ID;

@Repository("departmentRepoImpl")
public class DepartmentRepoImpl implements DepartmentRepo {

    private final Class<DepartmentModel> clazz = DepartmentModel.class;

    @Autowired
    private MongoUtility mongoUtility;

    @Autowired
    private DepartmentRepoMapper mapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("departments")
    private String collection;

    @Override
    public List<DepartmentModel> getAllDepartments(PaginationRequest paginationRequest) {
        Query query = new Query();
        MongoFilterUtility.addPagination(paginationRequest, query);
        return mongoTemplate.find(
                query,
                clazz,
                collection
        );
    }

    @Override
    public List<DepartmentModel> getDepartmentsByType(String deptType) {
        return mongoTemplate.find(
                Query.query(Criteria.where("departmentType").in(deptType)),
                clazz,
                collection
        );
    }

    @Override
    public DepartmentModel getDepartmentByCode(String deptCode) {
        return mongoTemplate.findOne(
                Query.query(Criteria.where("departmentCode").is(deptCode)),
                clazz,
                collection
        );
    }

    @Override
    public List<DepartmentModel> createDepartments(List<DepartmentModel> departmentModels) {
        List<DepartmentEntity> newDepartmentModels = mapper.toEntity(departmentModels);
        mongoTemplate.insert(newDepartmentModels, collection);
        return mapper.toModel(newDepartmentModels);
    }

    @Override
    public List<DepartmentModel> updateDepartments(List<DepartmentModel> departmentModels) {

        List<DepartmentEntity> departmentEntities = mapper.toEntity(departmentModels);

        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, clazz, collection);

        for (DepartmentEntity departmentEntity : departmentEntities) {
            bulkOps.updateOne(
                    Query.query(Criteria.where(ID).is(departmentEntity.getId())),
                   mongoUtility.updateMapper(departmentEntity, "")
            );
        }

        bulkOps.execute();
        return mapper.toModel(departmentEntities);
    }

    @Override
    public long deleteDepartments(List<String> ids) {
        Query query = new Query(Criteria.where(ID).in(mongoUtility.createObjectIdFromString(ids)));
        return mongoTemplate.remove(
                query,
                clazz,
                collection
        ).getDeletedCount();
    }
}
