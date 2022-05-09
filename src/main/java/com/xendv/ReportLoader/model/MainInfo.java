package com.xendv.ReportLoader.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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

/*    @JsonIgnore
    @OneToMany(mappedBy = "mainInfo", fetch=FetchType.EAGER)// lazy?
    private Collection<CompanyInfo> companyInfo;*/
}
