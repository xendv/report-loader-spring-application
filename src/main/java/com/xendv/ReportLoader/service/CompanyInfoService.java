package com.xendv.ReportLoader.service;

import com.xendv.ReportLoader.model.CompanyInfo;
import com.xendv.ReportLoader.repository.CustomCompanyInfoRepository;

import java.math.BigDecimal;
import java.util.List;

public class CompanyInfoService implements CustomCompanyInfoRepository {

    @Override
    public List<CompanyInfo> findAllByOkpo(BigDecimal okpo) {
        return null;
    }
}
