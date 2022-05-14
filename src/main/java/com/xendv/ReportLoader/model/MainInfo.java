package com.xendv.ReportLoader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@Table(schema = "main", name = "main_info")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamicUpdate
public class MainInfo {

    @Id
    @Column(name = "okpo", nullable = false, updatable = false)
    @CsvBindByName(required = true)
    public @NotNull BigDecimal okpo;

    @JsonProperty("name")
    @CsvBindByName
    @Column(name = "name")
    public String name;

    /*@JsonIgnore
    @OneToMany(mappedBy = "mainInfo", fetch=FetchType.EAGER)// lazy?
    //@OneToMany(mappedBy = "okpo", fetch=FetchType.EAGER)// lazy?
    @ToString.Exclude
    private Collection<CompanyInfo> companyInfo;*/
}
