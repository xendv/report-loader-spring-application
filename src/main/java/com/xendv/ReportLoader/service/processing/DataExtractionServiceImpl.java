package com.xendv.ReportLoader.service.processing;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.xendv.ReportLoader.exception.ExtractionException;
import com.xendv.ReportLoader.exception.ServerStateException;
import com.xendv.ReportLoader.model.FullInfo;
import com.xendv.ReportLoader.processor.CSVProcessor;
import com.xendv.ReportLoader.processor.DBFProcessor;
import com.xendv.ReportLoader.service.storage.FileSystemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service("dataExtractionService")
public class DataExtractionServiceImpl implements DataExtractionService {

    @Autowired
    private @NotNull FileSystemStorageService storageService;

    @Autowired
    public DataExtractionServiceImpl() {
    }

    public List<FullInfo> extract(@NotNull String filePath) {
        var file = storageService.loadAsFile(filePath);
        var extention = fileExtention(file.getName()).toLowerCase();
        if (extention.equals("csv")) {
            return dataFromCSVFile(filePath);
        }
        if (extention.equals("dbf")) {
            return dataFromDBFFile(filePath);
        }
        return null;
    }

    public List<FullInfo> dataFromCSVFile(@NotNull String filePath) {
        File file = storageService.loadAsFile(filePath);
        try {
            return CSVProcessor.process(file);
        } catch (FileNotFoundException e) {
            throw new ExtractionException("Файл не найден на сервере");
        } catch (UnrecognizedPropertyException e) {
            throw new ExtractionException("Ошибка обработки файла. Неизвестное поле: " + e.getPropertyName());
        } catch (IOException e) {
            throw new ExtractionException("Ошибка обработки файла: " + e.getCause().getLocalizedMessage());
        } catch (IllegalAccessException e) {
            throw new ServerStateException(" " + e.getCause().getLocalizedMessage());
        }
    }

    public List<FullInfo> dataFromDBFFile(@NotNull String filePath) {
        try (var fis = new FileInputStream(filePath)) {
            return DBFProcessor.process(fis);
        } catch (FileNotFoundException e) {
            throw new ExtractionException("Файл не найден на сервере");
        } catch (IOException e) {
            throw new ExtractionException("Ошибка обработки файла: " + e.getCause().getLocalizedMessage());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ServerStateException(" " + e.getCause().getLocalizedMessage());
        }
    }

    public String fileExtention(@NotNull String filename) {
        return Optional.of(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                .orElse("");
    }
}
