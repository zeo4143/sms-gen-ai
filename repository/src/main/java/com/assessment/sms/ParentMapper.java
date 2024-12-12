package com.assessment.sms;

import org.bson.types.ObjectId;
import org.mapstruct.IterableMapping;
import org.springframework.util.ObjectUtils;

import java.util.List;

public abstract class ParentMapper {

    public ParentMapper() {
    }

    @IterableMapping(
            elementTargetType = String.class
    )
    public abstract List<String> objectIdToString(List<ObjectId> objectId);

    public String objectIdToString(ObjectId objectId) {
        return objectId == null ? null : objectId.toString();
    }

    @IterableMapping(
            elementTargetType = ObjectId.class
    )
    public abstract List<ObjectId> stringtoObjectId(List<String> strings);

    public ObjectId stringtoObjectId(String string) {
        if (ObjectUtils.isEmpty(string)) {
            return null;
        } else if (!ObjectId.isValid(string)) {
            return null;
//            throw new InvalidObjectIDException("Invalid Object ID");
        } else {
            return new ObjectId(string);
        }
    }
}


