package com.xendv.ReportLoader.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullInfo implements Serializable {
    @Id
    @CsvBindByName(column = "id")
    @JsonProperty("id")
    public @Nullable
    BigDecimal id;

    @Column(nullable = false, insertable = false, updatable = false)
    @JsonProperty(value = "okpo", required = true)
    @CsvBindByName(required = true, column = "okpo")
    public @Nullable
    BigDecimal okpo;

    @CsvBindByName(column = "name")
    @JsonProperty("name")
    public @Nullable
    String name;

    @CsvBindByName(required = true)
    @JsonProperty("reporting_year")
    public @Nullable
    BigDecimal reporting_year;

    @CsvBindByName(column = "people")
    @JsonProperty("people")
    public @Nullable
    BigDecimal people;
    @CsvBindByName(column = "revenue")
    @JsonProperty("revenue")
    public @Nullable
    BigDecimal revenue;

    @CsvBindByName(column = "profit")
    @JsonProperty("profit")
    public @Nullable
    BigDecimal profit;
    @CsvBindByName(column = "salary")
    @JsonProperty("salary")
    public @Nullable
    BigDecimal salary;
}
