package com.xendv.ReportLoader.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.xendv.ReportLoader.model.FullInfo;
import com.xendv.ReportLoader.service.storage.FileSystemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DataExtractionService implements FileProcessingService {
    char colDelimiter = '|';
    char rowDelimiter = '\n';
    ArrayList<String> header = new ArrayList<>();

    @Autowired
    private @NotNull FileSystemStorageService storageService;

    @Autowired
    public DataExtractionService() {

    }


    public List<FullInfo> extract(@NotNull String filePath) {
        //var resource = storageService.loadAsResource(filePath);
        var file = storageService.loadAsFile(filePath);
        var extention = fileExtention(file.getName()).toLowerCase();
        if (extention.equals("csv")) {
            return dataFromCSVFile(filePath);
        }
        return null;
/*        if (extention.equals("dbf")) {
            dataFromDBFFile(filePath);
        }*/
        // return error
    }

    public List<FullInfo> dataFromCSVFile(@NotNull String filePath) {
/*        CsvToBean<CompanyInfo> csvToBean = new CsvToBeanBuilder()
                .withType(CompanyInfo.class)
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
        }*/
        File file = storageService.loadAsFile(filePath);
        try (var x = new FileInputStream(file)) {
            Reader reader = new BufferedReader(new InputStreamReader(x));
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator('|')
                    .build();
            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withCSVParser(parser)
                    .withSkipLines(0)
                    .build();
            List<String[]> list = new ArrayList<>();
            list = csvReader.readAll();
            reader.close();
            csvReader.close();

            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withUseHeader(true).withHeader().withColumnSeparator('|');
            CsvMapper mapper = new CsvMapper();
            MappingIterator<FullInfo> readValues =
                    mapper.readerFor(FullInfo.class).with(bootstrapSchema).readValues(file);

            // convert `CsvToBean` object to list of users
            List<FullInfo> companies = readValues.readAll();
            return companies;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }
        return null;
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
