package com.xendv.ReportLoader.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@Table(schema = "service", name = "indexes")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyInfo {
    @Id
    @GeneratedValue
    @CsvBindByName(required = true)
    private @NotNull BigDecimal id;
    @Column(nullable = false, insertable = false, updatable = false)
    @CsvBindByName(required = true)
    private @NotNull BigDecimal okpo;
    @CsvBindByName
    private @NotNull BigDecimal people;
    @CsvBindByName
    private @NotNull BigDecimal revenue;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "okpo", nullable = false)
    private @NotNull MainInfo mainInfo;
}
