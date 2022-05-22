package com.xendv.ReportLoader.service.processing;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.xendv.ReportLoader.exception.ExtractionException;
import com.xendv.ReportLoader.exception.ServerStateException;
import com.xendv.ReportLoader.model.CompanyInfo;
import com.xendv.ReportLoader.model.FullInfo;
import com.xendv.ReportLoader.service.storage.FileSystemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("dataExtractionService")
public class DataExtractionServiceImpl implements DataExtractionService {
    char colDelimiter = '|';
    char rowDelimiter = '\n';
    ArrayList<String> header = new ArrayList<>();

    @Autowired
    private @NotNull FileSystemStorageService storageService;

    @Autowired
    public DataExtractionServiceImpl() {
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
/*            CSVParser parser = new CSVParserBuilder()
                    .withSeparator('|')
                    .build();
            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withCSVParser(parser)
                    .withSkipLines(0)
                    .build();
            List<String[]> list = new ArrayList<>();
            list = csvReader.readAll();
            reader.close();
            csvReader.close();*/
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = CsvSchema.emptySchema().withUseHeader(true).withHeader().withColumnSeparator('|');
            //CsvSchema schema = mapper.schemaFor(FullInfo.class).withHeader().withColumnSeparator('|');
           /* MappingIterator<FullInfo> readValues =
                    mapper.readerFor(FullInfo.class).with(bootstrapSchema).readValues(file);*/
            MappingIterator<FullInfo> readValues =
                    mapper.readerFor(FullInfo.class).with(schema).readValues(file);
            var list = readValues.readAll();
            ObjectMapper objectMapper = new ObjectMapper();
            for (FullInfo info : list) {
                if (info.okpo == null) {
                    throw new ExtractionException("Ошибка обработки. Не найден ОКПО (поле okpo)");
                }
                var companyInfo = objectMapper.convertValue(info, CompanyInfo.class);
                if (companyInfo.year == null && !companyInfo.checkNull()) {
                    throw new ExtractionException("Ошибка обработки. Не задан год для добавления показателей " +
                            "(поле reporting_year)");
                }
            }
            return list;
        } catch (FileNotFoundException e) {
            throw new ExtractionException("Не найден файл на сервере");
        } catch (UnrecognizedPropertyException e) {
            throw new ExtractionException("Ошибка обработки файла. Неизвестное поле: " + e.getPropertyName());
        } catch (IOException e) {
            throw new ExtractionException("Ошибка обработки файла: " + e.getCause().getLocalizedMessage());
        } catch (IllegalAccessException e) {
            throw new ServerStateException(" " + e.getCause().getLocalizedMessage());
        }
    }

    public void dataFromDBFFile(@NotNull String filePath) {

    }

    public String fileExtention(@NotNull String filename) {
        return Optional.of(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                .orElse("");
    }
}
