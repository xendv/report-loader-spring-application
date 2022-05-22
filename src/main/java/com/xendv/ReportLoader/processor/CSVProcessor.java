package com.xendv.ReportLoader.processor;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.xendv.ReportLoader.model.FullInfo;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CSVProcessor extends FullInfoProcessor {
    public static List<FullInfo> process(@NotNull File file) throws IOException, IllegalAccessException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withUseHeader(true).withHeader().withColumnSeparator('|');
        MappingIterator<FullInfo> readValues =
                mapper.readerFor(FullInfo.class).with(schema).readValues(file);
        var infos = readValues.readAll();
        ObjectMapper objectMapper = new ObjectMapper();
        for (FullInfo info : infos) {
            validateRequiredFields(objectMapper, info);
        }
        return infos;
    }
}
