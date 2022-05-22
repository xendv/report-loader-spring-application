package com.xendv.ReportLoader.service.data;

import com.xendv.ReportLoader.model.CompanyInfo;
import io.micrometer.core.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public interface CompanyInfoService {

    @Nullable
    CompanyInfo create(CompanyInfo companyInfo);

    void updateValuesIfNotNull(@NotNull CompanyInfo companyInfo);

    @Nullable
    Iterable<CompanyInfo> get(@NotNull BigDecimal okpo);

    Iterable<CompanyInfo> getAll();

    void delete(@NotNull BigDecimal okpo);

    void update(@NotNull BigDecimal okpo, CompanyInfo companyInfo);

    String getState(CompanyInfo company);
}
