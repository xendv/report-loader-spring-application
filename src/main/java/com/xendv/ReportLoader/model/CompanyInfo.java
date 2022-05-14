package com.xendv.ReportLoader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@Table(schema = "main", name = "indexes")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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
    public @NotNull BigDecimal id;

    @CsvBindByName(required = true)
    @JsonProperty("reporting_year")
    @Column(name = "reporting_year", nullable = false)
    public @NotNull BigDecimal year;

    @CsvBindByName(required = true)
    @JsonProperty
    @Column(name = "okpo", nullable = false, updatable = false)
    //@JoinColumn(name = "okpo", nullable = false)//, referencedColumnName="okpo", table = "main_info"
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


/*
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "okpo", nullable = false, referencedColumnName="okpo")
    @JsonIgnore
    @ToString.Exclude
    private MainInfo mainInfo;

    @JsonIgnore
    //@Transient
    //@CsvBindByName(column = "name")
    public MainInfo getMainInfo() {
        return mainInfo;
    }

    //@CsvBindByName(required = true)
    @JsonProperty(value = "okpo")
    @JsonGetter
    @Column(name = "okpo")
    public BigDecimal getOkpo() {
        return getMainInfo().getOkpo();
    }

    @JsonSetter
    @Column(name = "okpo")
    public void setOkpo(BigDecimal okpo) {
        this.okpo = okpo;
    }
*/

/*
    @JsonProperty("name")
    @JsonGetter(value = "name")
    public @NotNull String getName() {
        return mainInfo.getName();
    }*/
}
