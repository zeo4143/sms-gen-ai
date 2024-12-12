package com.assessment.sms.commons.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class FilterField {

    private String dbKey;
    private FilterFieldType type;
}