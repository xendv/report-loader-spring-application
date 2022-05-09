package com.xendv.ReportLoader.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xendv.ReportLoader.model.CompanyInfo;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;

public class CompanyInfoProcessor implements ItemProcessor<CompanyInfo, String> {
    @Override
    public String process(@NonNull CompanyInfo item) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(item);
    }
}
