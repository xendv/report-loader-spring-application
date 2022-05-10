package com.xendv.ReportLoader.service.data;

import com.xendv.ReportLoader.model.CompanyInfo;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Service("companyInfoService")
public interface CompanyInfoService {

    Iterable<CompanyInfo> get(@NotNull BigDecimal okpo);

    Iterable<CompanyInfo> getAll();

    void delete(@NotNull BigDecimal okpo);

    void update(@NotNull BigDecimal okpo, CompanyInfo companyInfo);
}
