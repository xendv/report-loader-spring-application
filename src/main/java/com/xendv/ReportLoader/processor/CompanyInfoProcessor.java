package com.xendv.ReportLoader.processor;

import com.xendv.ReportLoader.model.CompanyInfo;
import org.springframework.batch.item.ItemProcessor;

public class CompanyInfoProcessor implements ItemProcessor<CompanyInfo, CompanyInfo>  {
    @Override
    public CompanyInfo process(CompanyInfo item) throws Exception {
        return item;
    }
}
