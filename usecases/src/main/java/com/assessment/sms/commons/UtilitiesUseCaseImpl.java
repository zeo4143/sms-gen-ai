package com.assessment.sms.commons;

import com.assessment.sms.commons.usecases.UtilitiesUseCase;
import org.springframework.stereotype.Service;

@Service("utilitiesUseCaseImpl")
public class UtilitiesUseCaseImpl implements UtilitiesUseCase {

    @Override
    public String extractParamFromKey(String param) {
        return param.replaceAll("^params\\[(.*)\\]$", "$1");
    }
}
