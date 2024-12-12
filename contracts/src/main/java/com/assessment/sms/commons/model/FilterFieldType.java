package com.assessment.sms.commons.model;

import java.util.Arrays;
import java.util.List;

public enum FilterFieldType {
    REGEX, DATE_RANGE, LIST, ID_LIST, NUMBER, EXACT, EXISTS, NOT_EXISTS;

    public static List<FilterFieldType> withOutValueType() {
        return Arrays.asList(NOT_EXISTS, EXISTS, DATE_RANGE);
    }
}
