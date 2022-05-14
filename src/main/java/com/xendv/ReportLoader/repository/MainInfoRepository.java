package com.xendv.ReportLoader.repository;

import com.xendv.ReportLoader.model.MainInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface MainInfoRepository extends PagingAndSortingRepository<MainInfo, BigDecimal> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE main.main_info SET " +
            "okpo =  COALESCE(:okpo, okpo), " +
            "name = COALESCE(CAST(:name AS TEXT), name) " +
            "WHERE okpo = :okpo", nativeQuery = true)
    void updateValues(@Param("okpo") BigDecimal okpo,
                      @Param("name") String name);

}
