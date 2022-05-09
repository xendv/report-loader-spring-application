package com.xendv.ReportLoader.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collection;

@Data
@Entity
@Table(schema = "service", name = "main_info")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MainInfo {
    @Id
    @Column(nullable = false)
    @CsvBindByName(required = true)
    private @NotNull BigDecimal okpo;
    @CsvBindByName
    private String name;

    @OneToMany(mappedBy = "mainInfo", fetch=FetchType.EAGER)// lazy?
    private Collection<CompanyInfo> companyInfo;
}
