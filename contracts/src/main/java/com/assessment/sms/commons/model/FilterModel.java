package com.assessment.sms.commons.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class FilterModel {

    @NotBlank
    private String key;
    private Object value;
    private Date startDate;
    private Date endDate;

    public FilterModel(@NotBlank String key, Object value) {
        this.key = key;
        this.value = value;
    }
}
