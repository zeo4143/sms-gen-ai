package com.assessment.sms.studentgrades.mongo.repo;

import com.assessment.sms.commons.mongo.utility.MongoUtility;
import com.assessment.sms.studentgrades.model.GPAModel;
import com.assessment.sms.studentgrades.model.GPAResult;
import com.assessment.sms.studentgrades.mongo.mapper.GPARepoMapper;
import com.assessment.sms.studentgrades.repo.GPARepo;
import com.mongodb.BasicDBObject;
import org.bson.BsonObjectId;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.assessment.sms.commons.mongo.utility.MongoFieldConstants.ID;

@Repository("GPARepoImpl")
public class GPARepoImpl implements GPARepo {

    private static final Class<GPAModel> clazz = GPAModel.class;
    private static final String REG_NO = "regNo";

    @Autowired
    private GPARepoMapper mapper;

    @Autowired
    private MongoUtility mongoUtility;


    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("grades")
    private String collection;

    @Override
    public List<GPAResult> getGPAByRegNo(String regNo, int semester) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        aggregationOperations.add(Aggregation.match(Criteria.where(REG_NO).is(regNo)));
        aggregationOperations.add(Aggregation.project("tgpa").andExclude(ID));
        aggregationOperations.add(Aggregation.unwind("tgpa"));
        aggregationOperations.add(Aggregation.match(Criteria.where("tgpa.semester").is(semester)));
        aggregationOperations.add(Aggregation.unwind("tgpa.gpa"));
        aggregationOperations.add(Aggregation.lookup("courses", "tgpa.gpa.courseCode", "courseCode", "courses"));
        aggregationOperations.add(Aggregation.addFields()
                .addField("tgpa.gpa.courseName")
                .withValue(
                      ArrayOperators.ArrayElemAt
                              .arrayOf("$courses.courseName")
                              .elementAt(0))
                .build()
        );
        aggregationOperations.add(Aggregation.project()
                .and("tgpa.gpa.courseCode").as("courseCode")
                .and("tgpa.gpa.courseName").as("courseName")
                .and("tgpa.gpa.examResults").as("examresults")
                .and("tgpa.gpa.gpa").as("gpa")
                .and("tgpa.gpa.grade").as("grade")
                .and("tgpa.gpa.gradeDescription").as("gradeDescription")
                .and("tgpa.gpa.marksObtained").as("marksObtained")
                .and("tgpa.gpa.maxMarks").as("maxMarks")
        );

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);

        return mongoTemplate.aggregate(aggregation, collection, GPAResult.class).getMappedResults();
    }

    @Override
    public GPAModel updateGPAByRegNos(String regNo, int semester, GPAModel gpaModel) {
//        Query query = Query.query(Criteria.where(REG_NO).is(regNo)
//                .and("tgpa.semester").is(semester)
//                .and("tgpa.gpa.courseCode").is(gpaModel.getCourseCode())
//        );
//        Update update = mongoUtility.updateMapper(gpaModel, "tgpa.$.gpa.$[elem].");
//        UpdateOptions updateOptions = new UpdateOptions()
//                .arrayFilters(List.of(new Document("elem.courseCode", gpaModel.getCourseCode())));
//        return mongoTemplate.updateMulti(
//                query,
//                update,
//                collection,
//                updateOptions
//        );
        return null;
    }
}
