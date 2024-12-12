package com.assessment.sms.commons.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FilterRequest extends PaginationRequest {

    @Valid
    @ApiModelProperty(hidden = true)
    private List<FilterModel> filter;
}
