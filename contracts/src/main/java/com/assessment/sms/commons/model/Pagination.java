package com.assessment.sms.commons.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination {

    private int start;
    private int end;
    private boolean paginationRequired;

    private Pagination(int start, int end, boolean paginationRequired) {
        this.start = start;
        this.end = end;
        this.paginationRequired = paginationRequired;
    }

    public static Pagination get(Integer pageNumber, Integer pageSize) {
        int start = -1;
        int end = -1;
        boolean paginationRequired = false;
        if ((pageNumber != null && pageSize != null) && (pageNumber != -1 && pageSize != -1)) {
            start = ((pageNumber - 1) * pageSize);
            end = pageSize;
            paginationRequired = true;
        }
        return new Pagination(start, end, paginationRequired);
    }

    public static Pagination get(PaginationRequest request) {
        Integer pageNumber = null;
        Integer pageSize = null;
        if (request != null) {
            pageNumber = request.getPageNumber();
            pageSize = request.getPageSize();
        }
        return get(pageNumber, pageSize);
    }
}
