package com.assessment.sms.studentgrades.mongo.repo;

import com.assessment.sms.commons.model.FilterRequest;
import com.assessment.sms.commons.mongo.utility.MongoUtility;
import com.assessment.sms.studentgrades.model.CGPAModel;
import com.assessment.sms.studentgrades.model.TGPAListResponse;
import com.assessment.sms.studentgrades.model.TGPAModel;
import com.assessment.sms.studentgrades.model.TGPAResponse;
import com.assessment.sms.studentgrades.repo.TGPARepo;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import static com.assessment.sms.commons.mongo.utility.MongoFieldConstants.ID;
import static org.springframework.data.mongodb.core.aggregation.ArrayOperators.Filter.filter;

import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("studentTGPARepoImpl")
public class TGPARepoImpl implements TGPARepo {

    private final Class<CGPAModel> cClazz = CGPAModel.class;
    private final Class<TGPAModel> tClazz = TGPAModel.class;
    private final String REG_NO = "regNo";
    private final String TGPA = "tgpa";


    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("grades")
    private String collection;
    @Autowired
    private MongoUtility mongoUtility;

    @Override
    public List<TGPAResponse> getStudentTGPAs(String regNo) {
        List<AggregationOperation> aggregationOperation = new ArrayList<>();

        aggregationOperation.add(Aggregation.match(Criteria.where(REG_NO).is(regNo)));
        aggregationOperation.add(Aggregation.unwind(TGPA));
        aggregationOperation.add(Aggregation.project()
                .and("tgpa.semester").as("semester")
                .and("tgpa.tgpa").as("tgpa")
                .andExclude(ID)
        );

        aggregationOperation.add(Aggregation.sort(Sort.by(Sort.Direction.ASC, "semester")));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperation);

        return mongoTemplate.aggregate(aggregation, collection, TGPAResponse.class).getMappedResults();
    }

    @Override
    public List<TGPAListResponse> getStudentsTGPABySemester(List<String> regNo, int semester, FilterRequest request) {

        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        aggregationOperations.add(Aggregation.match(Criteria.where(REG_NO).in(regNo)));
        aggregationOperations.add(Aggregation.unwind(TGPA));
        aggregationOperations.add(Aggregation.match(Criteria.where("tgpa.semester").is(semester)));
        aggregationOperations.add(Aggregation.project(REG_NO, "tgpa.tgpa").andExclude(ID));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);

        return mongoTemplate.aggregate(
                aggregation,
                collection,
                TGPAListResponse.class
        ).getMappedResults();
    }

    @Override
    public long calculateTGPAAndUpdateCGPA(List<String> deptCodes, List<Integer> semester) {

        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        aggregationOperations.add(Aggregation.match(Criteria.where("academicRecords.departmentCode").in(deptCodes)));
        aggregationOperations.add(Aggregation.project(REG_NO).andExclude(ID));
        aggregationOperations.add(Aggregation.lookup(collection, REG_NO, REG_NO, collection));
        aggregationOperations.add(Aggregation.unwind(collection));
        aggregationOperations.add(Aggregation.unwind("grades.tgpa"));
        aggregationOperations.add(Aggregation.addFields()
                .addField("grades.tgpa.tgpa")
                .withValue(
                        ArithmeticOperators.Round.roundValueOf(
                                AccumulatorOperators.Avg.avgOf("grades.tgpa.gpa.gpa")
                        ).place(2)
                )
                .build()
        );
        aggregationOperations.add(Aggregation.group("grades._id")
                .first("grades.regNo")
                .as("regNo")
                .push( new BasicDBObject("semester", "$grades.tgpa.semester")
                                .append("tgpa", "$grades.tgpa.tgpa")
                                .append("gpa", "$grades.tgpa.gpa")
                        )
                .as("tgpa")
        );
        aggregationOperations.add(Aggregation.addFields()
                .addField("cgpa")
                .withValue(
                        ArithmeticOperators.Round.roundValueOf(
                                AccumulatorOperators.Avg.avgOf("tgpa.tgpa")
                        ).place(2)
                )
                .build()
        );
        aggregationOperations.add(Aggregation
                .merge()
                .intoCollection(collection)
                .on(ID)
                .whenMatched(MergeOperation.WhenDocumentsMatch.mergeDocuments())
                .whenNotMatched(MergeOperation.WhenDocumentsDontMatch.insertNewDocument())
                .build()
        );

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);

        return mongoTemplate.aggregate(aggregation, "students", cClazz).getMappedResults().size();
    }
}
