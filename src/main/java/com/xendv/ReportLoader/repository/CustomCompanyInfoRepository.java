package com.xendv.ReportLoader.repository;

import com.xendv.ReportLoader.model.CompanyInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CustomCompanyInfoRepository {
    @Query(value = "SELECT e FROM CompanyInfo e where e.okpo = :okpo")
    List<CompanyInfo> findAllByOkpo(@Param("okpo") BigDecimal okpo);
}
