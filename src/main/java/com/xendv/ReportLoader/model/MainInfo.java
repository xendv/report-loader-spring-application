package com.xendv.ReportLoader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainInfo {
    @JsonProperty("name")
    @CsvBindByName
    public String name;
    @Id
    @Column(name = "okpo", nullable = false)
    @CsvBindByName(required = true)
    private @NotNull BigDecimal okpo;

    /*@JsonIgnore
    @OneToMany(mappedBy = "mainInfo", fetch=FetchType.EAGER)// lazy?
    //@OneToMany(mappedBy = "okpo", fetch=FetchType.EAGER)// lazy?
    @ToString.Exclude
    private Collection<CompanyInfo> companyInfo;*/
}
