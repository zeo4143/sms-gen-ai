package com.assessment.sms.commons.mongo.utility;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

public class AggregationOperationImpl implements AggregationOperation {

    private Document doc;

    public AggregationOperationImpl(Document doc) {
        this.doc = doc;
    }

    @Override
    public Document toDocument(AggregationOperationContext aggregationOperationContext) {
        return doc;
    }
}
