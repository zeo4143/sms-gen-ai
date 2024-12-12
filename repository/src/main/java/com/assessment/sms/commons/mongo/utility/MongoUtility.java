package com.assessment.sms.commons.mongo.utility;

import com.assessment.sms.commons.usecases.UtilitiesUseCase;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.assessment.sms.commons.mongo.utility.MongoFieldConstants.ID;

@Service("mongoUtility")
public class MongoUtility {
    
    @Autowired
    private UtilitiesUseCase utilitiesUseCase;

    public static Query includeProjectionFieldsById(String objectId, List<String> includeFields, Boolean includeObjectId) {
        Query query = new Query(Criteria.where(ID).is(new ObjectId(objectId)));
        includeFields.forEach(field -> query.fields().include(field));
        if (!includeObjectId) query.fields().exclude(objectId);
        return query;
    }

    public static Query excludeProjectionFieldsById(String objectId, List<String> excludeFields, Boolean excludeObjectId) {
        Query query = new Query(Criteria.where(ID).is(new ObjectId(objectId)));
        excludeFields.forEach(field -> query.fields().exclude(field));
        if (excludeObjectId) query.fields().exclude(objectId);
        return query;
    }

    public static Query filterProjectionFieldsById(
            String objectId,
            List<String> includeFields,
            Map<String, String> filterConditions,
            Boolean includeObjectId) {

        Query query = includeProjectionFieldsById(objectId, includeFields, includeObjectId);

        Criteria criteria = new Criteria();
        filterConditions.forEach((k,v) -> criteria.and(k).is(v));
        query.addCriteria(criteria);

        return query;
    }



    public  Update updateMapper(Object entity, String appender) {
        Update update = new Update();



        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                if("_id".equals(field.getName())) {
                    continue;
                }

                field.setAccessible(true);
                Object fieldValue = field.get(entity);
                if (fieldValue != null) {
                    update.set(appender+field.getName(), fieldValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return update;
    }

    public Query createQueryFromParams(Map<String, Object> params, String nestedKey) {
        Query query = new Query();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = utilitiesUseCase.extractParamFromKey(nestedKey.isEmpty() ? entry.getKey() : nestedKey + "." + entry.getKey());
            query.addCriteria(Criteria.where(key).is(entry.getValue()));
        }
        return query;
    }

    public Query createQueryFromParams(Map<String, Object> params) {
        Query query = new Query();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = utilitiesUseCase.extractParamFromKey(entry.getKey());
            query.addCriteria(Criteria.where(key).is(entry.getValue()));
        }
        return query;
    }

    public Query createQueryFromIds(List<String> ids) {
        return Query.query(Criteria.where(ID).in(createObjectIdFromString(ids)));
    }

    public List<ObjectId> createObjectIdFromString(List<String> objectIds) {
        return objectIds.stream().map(ObjectId::new).collect(Collectors.toList());
    }
}
