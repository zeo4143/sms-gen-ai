package com.assessment.sms.studentgrades.request;

import com.assessment.sms.commons.model.FilterRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TGPAListRequest extends FilterRequest {
   private List<String> regNos;
   private Integer semester;
}
