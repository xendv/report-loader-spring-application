package com.xendv.ReportLoader.service;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.xendv.ReportLoader.model.MainInfo;
import com.xendv.ReportLoader.service.storage.FileSystemStorageService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class DataExtractionService implements FileProcessingService {
    char colDelimiter = '|';
    char rowDelimiter = '\n';
    ArrayList<String> header = new ArrayList<>();

    private @NotNull FileSystemStorageService storageService;

    public void extract(@NotNull String filePath) {
        //var resource = storageService.loadAsResource(filePath);
        var file = storageService.loadAsFile(filePath);
        var extention = fileExtention(file.getName()).toLowerCase();
        if (extention.equals("csv")) {
            dataFromCSVFile(file);
        }
        if (extention.equals("dbf")) {
            dataFromDBFFile(file);
        }
        // return error
    }

    public void dataFromCSVFile(Class<T> type, @NotNull String filePath) {
        CsvToBean<MainInfo> csvToBean = new CsvToBeanBuilder(reader)
                .withType(MainInfo.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            //File file = new ClassPathResource(filePath).getFile();
            var file = storageService.loadAsFile(filePath);
            MappingIterator<T> readValues =
                    mapper.reader(type).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            logger.error("Error occurred while loading object list from file " + fileName, e);
            return Collections.emptyList();
        }
    }

    public void dataFromDBFFile(@NotNull String filePath) {

    }

    public String fileExtention (@NotNull String filename) {
        return Optional.of(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                .orElse("");
    }
}
