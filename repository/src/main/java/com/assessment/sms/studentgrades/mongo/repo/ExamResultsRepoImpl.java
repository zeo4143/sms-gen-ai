package com.assessment.sms.studentgrades.mongo.repo;

import com.assessment.sms.studentgrades.model.CGPAModel;
import com.assessment.sms.studentgrades.model.ExamResultsModel;
import com.assessment.sms.studentgrades.model.GPAModel;
import com.assessment.sms.studentgrades.model.TGPAModel;
import com.assessment.sms.studentgrades.mongo.mapper.ExamResultsGPAMapper;
import com.assessment.sms.studentgrades.repo.ExamResultsRepo;
import com.assessment.sms.studentgrades.usecase.GPACalculatorUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository("examResultsRepoImpl")
public class ExamResultsRepoImpl implements ExamResultsRepo {

    private static final String REG_NO = "regNo";

    @Autowired
    private ExamResultsGPAMapper examResultsGPAMapper;

    @Autowired
    private GPACalculatorUseCase gpaCalculator;


    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("grades")
    private String collection;


    @Override
    public long insertExamResults(List<ExamResultsModel> examResults) {

        Map<String, Map<Integer, GPAModel>> regNoToSemesterGPA = new HashMap<>();

        for (ExamResultsModel examResult : examResults) {
            GPAModel gpaModel = gpaCalculator.calculateGPA(examResultsGPAMapper.toModel(examResult));

            regNoToSemesterGPA
                    .computeIfAbsent(examResult.getRegNo(), k -> new HashMap<>())
                    .put(examResult.getSemester(), gpaModel);
        }

        List<String> regNos = examResults.stream()
                .map(ExamResultsModel::getRegNo)
                .distinct()
                .collect(Collectors.toList());


        Query query = new Query(Criteria.where(REG_NO).in(regNos));
        List<CGPAModel> existingCgpaModels = mongoTemplate.find(query, CGPAModel.class, collection);


        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, CGPAModel.class, collection);

        long updatedCount = 0;
        List<CGPAModel> newCgpaModels = new ArrayList<>();

        for (ExamResultsModel examResult : examResults) {
            String regNo = examResult.getRegNo();
            int semester = examResult.getSemester();
            GPAModel gpaModel = regNoToSemesterGPA.get(regNo).get(semester);

            CGPAModel cgpaModel = existingCgpaModels.stream()
                    .filter(c -> c.getRegNo().equals(regNo))
                    .findFirst()
                    .orElse(null);

            if (cgpaModel != null) {

                TGPAModel existingTgpaModel = cgpaModel.getTgpa().stream()
                        .filter(t -> t.getSemester() == semester)
                        .findFirst()
                        .orElse(null);


                if (existingTgpaModel != null) {

                    bulkOps.updateOne(
                            new Query(Criteria.where(REG_NO).is(regNo).and("tgpa.semester").is(semester)),
                            new Update().push("tgpa.$.gpa", gpaModel)
                    );
                } else {

                    TGPAModel newTgpaModel = new TGPAModel();
                    newTgpaModel.setGpa(List.of(gpaModel));
                    newTgpaModel.setSemester(semester);

                    bulkOps.updateOne(
                            new Query(Criteria.where(REG_NO).is(regNo)),
                            new Update().push("tgpa", newTgpaModel)
                    );

                }
                updatedCount++;
            } else {

                TGPAModel tgpaModel = new TGPAModel();
                tgpaModel.setGpa(List.of(gpaModel));
                tgpaModel.setSemester(semester);

                CGPAModel newCgpaModel = new CGPAModel();
                newCgpaModel.setRegNo(regNo);
                newCgpaModel.setTgpa(List.of(tgpaModel));


                newCgpaModels.add(newCgpaModel);
            }
        }

        if (updatedCount > 0) {
            bulkOps.execute();
        }

        if (!newCgpaModels.isEmpty()) {
            System.out.println("newCgpaModels " + newCgpaModels);
            for (CGPAModel cgpaModel : newCgpaModels) {
                mongoTemplate.insert(cgpaModel, collection);
            }
            updatedCount += newCgpaModels.size();
        }

        return updatedCount;
    }

}

