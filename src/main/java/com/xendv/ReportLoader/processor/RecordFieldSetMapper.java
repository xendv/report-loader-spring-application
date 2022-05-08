package com.xendv.ReportLoader.processor;

import com.xendv.ReportLoader.model.CompanyInfo;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import javax.validation.constraints.Negative;
import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;

public final class RecordFieldSetMapper implements FieldSetMapper<CompanyInfo> {

    public CompanyInfo mapFieldSet(@NotNull FieldSet fieldSet) {//throws BindException {
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyy");
        CompanyInfo companyInfo = new CompanyInfo();

        companyInfo.setOkpo(fieldSet.readBigDecimal("okpo"));
        String dateString = fieldSet.readString(2);
        //companyInfo.setTransactionDate(LocalDate.parse(dateString, formatter).atStartOfDay());
        return companyInfo;
    }
}