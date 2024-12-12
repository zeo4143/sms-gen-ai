package com.assessment.sms.commons.model;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PaginationRequest {
    @ApiModelProperty(
            notes = "Page number for which user wants to get data.",
            example = "1"
    )
    private @NotNull Integer pageNumber;
    @ApiModelProperty(
            notes = "Specifies page size.",
            example = "5"
    )
    private @NotNull Integer pageSize;
    private List<SortModel> sort;
    @ApiModelProperty(
            hidden = true
    )
    private String searchText;

    public PaginationRequest(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public List<SortModel> getSort() {
        return this.sort;
    }

    public String getSearchText() {
        return this.searchText;
    }

    public void setPageNumber(final Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(final Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setSort(final List<SortModel> sort) {
        this.sort = sort;
    }

    public void setSearchText(final String searchText) {
        this.searchText = searchText;
    }

    public PaginationRequest() {
    }

    public PaginationRequest(final Integer pageNumber, final Integer pageSize, final List<SortModel> sort, final String searchText) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = sort;
        this.searchText = searchText;
    }
}

