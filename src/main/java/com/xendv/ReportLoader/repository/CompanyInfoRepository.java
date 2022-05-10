package com.xendv.ReportLoader.repository;

import com.xendv.ReportLoader.model.CompanyInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CompanyInfoRepository extends PagingAndSortingRepository<CompanyInfo, BigDecimal> {
}
