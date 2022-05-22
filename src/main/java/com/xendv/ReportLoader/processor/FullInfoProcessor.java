package com.xendv.ReportLoader.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xendv.ReportLoader.exception.ExtractionException;
import com.xendv.ReportLoader.model.CompanyInfo;
import com.xendv.ReportLoader.model.FullInfo;

public abstract class FullInfoProcessor {
    protected static void validateRequiredFields(ObjectMapper objectMapper, FullInfo info) throws IllegalAccessException {
        if (info.okpo == null) {
            throw new ExtractionException("Ошибка обработки. Не найден ОКПО (поле okpo)");
        }
        var companyInfo = objectMapper.convertValue(info, CompanyInfo.class);
        if (companyInfo.year == null && !companyInfo.checkNull()) {
            throw new ExtractionException("Ошибка обработки. Не задан год для добавления показателей " +
                    "(поле reporting_year)");
        }
    }
}
