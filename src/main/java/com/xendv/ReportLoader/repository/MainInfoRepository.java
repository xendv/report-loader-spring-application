package com.xendv.ReportLoader.repository;

import com.xendv.ReportLoader.model.MainInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface MainInfoRepository extends PagingAndSortingRepository<MainInfo, BigDecimal> {
}
