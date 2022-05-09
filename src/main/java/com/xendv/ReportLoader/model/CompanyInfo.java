package com.xendv.ReportLoader.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty
    private @NotNull BigDecimal id;
    @Column(nullable = false, insertable = false, updatable = false)
    @CsvBindByName(required = true)
    @JsonProperty
    private @NotNull BigDecimal okpo;
    @CsvBindByName
    @JsonProperty
    private @NotNull BigDecimal people;
    @CsvBindByName
    @JsonProperty
    private @NotNull BigDecimal revenue;

    @CsvBindByName(column = "profit")
    @JsonProperty("profit")
    private @NotNull BigDecimal profit;
    @CsvBindByName(column = "salary")
    @JsonProperty("salary")
    private @NotNull BigDecimal salary;

/*    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "okpo", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private @NotNull MainInfo mainInfo;*/
}
