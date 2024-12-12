package com.assessment.sms.commons.mongo.utility;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.assessment.sms.commons.mongo.utility.MongoFilterUtility.filter;

@Service("aggregationUtility")
public class AggregationUtility {

    public  Aggregation createAggregationPipeline(AggregationOperation... aggregations) {
        return Aggregation.newAggregation(aggregations);
    }

    public AggregationOperation injectMatchOperation(Map<String, List<Object>> matchOperation) {
        Criteria criteria = new Criteria();
        matchOperation.forEach( (k,v) -> {
            if (v != null && !v.isEmpty()) {

                if (v.size() == 1) {
                    criteria.and(k).is(v.get(0));
                } else {
                    criteria.and(k).in(v);
                }
            } else {
                criteria.and(k).exists(true); // This matches documents where the field exists
            }
        });
        return Aggregation.match(criteria);
    }

    public AggregationOperation injectProjectionIncludeOption(String... projectionOptions) {
        return Aggregation.project(projectionOptions);
    }

    public AggregationOperation injectProjectionExcludeOption(String... projectionOptions) {
        return Aggregation.project().andExclude(projectionOptions);
    }

    public AggregationOperation injectGroupingOperation(String... groupingFields) {
        return Aggregation.group(groupingFields);
    }

    public AggregationOperation injectSortAscOperation(String... sortFields) {
        return Aggregation.sort((Sort) Arrays.stream(sortFields).map(Sort.Order::asc));
    }

    public static AggregationOperation injectSortDescOperation(String... sortFields) {
        return Aggregation.sort((Sort) Arrays.stream(sortFields).map(Sort.Order::desc));
    }

    public AggregationOperation injectLimitOperation(int limit) {
        return Aggregation.limit(limit);
    }

    public AggregationOperation injectSkipOperation(int skip) {
        return Aggregation.skip(skip);
    }

    public AggregationOperation injectLookupOperation(String from, String localField, String foreignField, String as) {
        return Aggregation.lookup(from, localField, foreignField, as);
    }
    
    public AggregationOperation injectSetOperation(String setField, String setValue) {
        return Aggregation.addFields().addField(setField).withValue(setValue).build();
    }

    public AggregationOperation injectArrayFilterOperation(String arrayField, String filterField, Object filterValue) {
//        Aggregation aggregation = Aggregation.newAggregation(Aggregation.project().and(filter()))
        return Aggregation.match(Criteria.where(arrayField).elemMatch(Criteria.where(filterField).is(filterValue)));
    }
}

