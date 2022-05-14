package com.xendv.ReportLoader.repository;

import com.xendv.ReportLoader.model.CompanyInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyInfoExtendedRepository extends CompanyInfoRepository {

    @Query(value = "SELECT * FROM main.indexes where okpo = ?1", nativeQuery = true)
    List<CompanyInfo> findAllByOkpo(@Param("okpo") BigDecimal okpo);

    @Query(value = "SELECT * FROM main.indexes WHERE okpo = :okpo AND reporting_year = :year", nativeQuery = true)
    Optional<CompanyInfo> findByOkpoAndYear(@NotNull BigDecimal okpo, @Nullable BigDecimal year);

    @Modifying
    @Transactional
    @Query(value = "UPDATE main.indexes SET " +
            "okpo =  COALESCE(:okpo, okpo), " +
            "people = COALESCE(:people, people), " +
            "revenue = COALESCE(:revenue, revenue), " +
            "profit = COALESCE(:profit, profit), " +
            "salary = COALESCE(:salary, salary) " +
            "WHERE okpo = :okpo AND reporting_year = :year", nativeQuery = true)
    void updateValues(@Param("okpo") BigDecimal okpo,
                      @Param("year") BigDecimal year,
                      @Param("people") BigDecimal people,
                      @Param("revenue") BigDecimal revenue,
                      @Param("profit") BigDecimal profit,
                      @Param("salary") BigDecimal salary);

}
