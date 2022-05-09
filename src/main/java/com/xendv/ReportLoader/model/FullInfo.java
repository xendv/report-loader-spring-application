package com.xendv.ReportLoader.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FullInfo implements Serializable {
    @Id
    @GeneratedValue
    @CsvBindByName(column = "id")
    @JsonProperty("id")
    private @NotNull BigDecimal id;
    @Column(nullable = false, insertable = false, updatable = false)
    @JsonProperty(value = "okpo", required = true)
    @CsvBindByName(required = true, column = "okpo")
    private @NotNull BigDecimal okpo;
    @CsvBindByName(column = "name")
    @JsonProperty("name")
    private String name;

    @CsvBindByName(column = "people")
    @JsonProperty("people")
    private @NotNull BigDecimal people;
    @CsvBindByName(column = "revenue")
    @JsonProperty("revenue")
    private @NotNull BigDecimal revenue;

    @CsvBindByName(column = "profit")
    @JsonProperty("profit")
    private @NotNull BigDecimal profit;
    @CsvBindByName(column = "salary")
    @JsonProperty("salary")
    private @NotNull BigDecimal salary;
}
