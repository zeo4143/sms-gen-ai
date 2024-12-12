package com.assessment.sms.commons.model;

import io.swagger.annotations.ApiModelProperty;

public class SortModel {
    @ApiModelProperty(
            notes = "The property according to which sorting is to be performed",
            example = "modifiedOn"
    )
    private String property;
    private SortDirection direction;

    public String getProperty() {
        return this.property;
    }

    public SortDirection getDirection() {
        return this.direction;
    }

    public void setProperty(final String property) {
        this.property = property;
    }

    public void setDirection(final SortDirection direction) {
        this.direction = direction;
    }

    public SortModel() {
    }

    public SortModel(final String property, final SortDirection direction) {
        this.property = property;
        this.direction = direction;
    }
}

