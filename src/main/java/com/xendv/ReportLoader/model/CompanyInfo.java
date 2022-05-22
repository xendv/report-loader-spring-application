package com.xendv.ReportLoader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.math.BigDecimal;

@Data
@Entity
@Table(schema = "main", name = "indexes")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
public class CompanyInfo {
    /*    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @CsvBindByName(required = true)
        @JsonProperty
        @JsonIgnore
        @Column(name = "id", nullable = false, insertable = false, updatable = false)
        private BigDecimal id;*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @CsvBindByName(column = "id")
    @JsonProperty("id")
    @EqualsAndHashCode.Exclude
    public @NotNull BigDecimal id;

    @CsvBindByName(required = true)
    @JsonProperty(value = "reporting_year", required = true)
    @Column(name = "reporting_year", nullable = false)
    public @NotNull BigDecimal year;

    @CsvBindByName(required = true)
    @JsonProperty
    @Column(name = "okpo", nullable = false, updatable = false)
    public @NotNull BigDecimal okpo;

    @CsvBindByName
    @JsonProperty
    public BigDecimal people;
    @CsvBindByName
    @JsonProperty
    public BigDecimal revenue;

    @CsvBindByName(column = "profit")
    @JsonProperty("profit")
    public BigDecimal profit;
    @CsvBindByName(column = "salary")
    @JsonProperty("salary")
    public BigDecimal salary;

    public boolean checkNull() throws IllegalAccessException {
        for (Field f : getClass().getDeclaredFields())
            if (f.get(this) != null) {
                if (!f.getName().equals("okpo")) {
                    return false;
                }
            }
        return true;
    }
}
