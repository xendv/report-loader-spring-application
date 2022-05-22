package com.xendv.ReportLoader.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.xendv.ReportLoader.exception.ExtractionException;
import com.xendv.ReportLoader.model.FullInfo;
import org.springframework.lang.NonNull;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DBFProcessor extends FullInfoProcessor {
    public static List<FullInfo> process(@NonNull FileInputStream fis)
            throws NoSuchFieldException, IllegalAccessException {
        List<FullInfo> infos = new ArrayList<>();
        DBFReader reader = new DBFReader(fis, StandardCharsets.UTF_8);
        int numberOfFields = reader.getFieldCount();
        var headers = Arrays.stream(FullInfo.class.getFields())
                .map(Field::getName)
                .collect(Collectors.toList());
        var actualHeaders = new ArrayList<String>();
        for (int i = 0; i < numberOfFields; i++) {
            DBFField field = reader.getField(i);
            var fieldName = field.getName().toLowerCase();
            System.out.println(fieldName);
            if (!headers.contains(fieldName)) {
                if (!fieldName.equals("reporting_")) {
                    throw new ExtractionException("Ошибка обработки файла. Неизвестное поле: " + fieldName);
                }
            }
            if (!fieldName.equals("reporting_")) {
                actualHeaders.add(fieldName);
            } else {
                actualHeaders.add("reporting_year");
            }
        }
        Object[] rowObjects;
        ObjectMapper objectMapper = new ObjectMapper();
        while ((rowObjects = reader.nextRecord()) != null) {
            FullInfo info = new FullInfo();
            for (int i = 0; i < rowObjects.length; i++) {
                System.out.println(rowObjects[i]);
                Field field = FullInfo.class.getField(actualHeaders.get(i));
                field.set(info, rowObjects[i]);
            }
            validateRequiredFields(objectMapper, info);
            infos.add(info);
        }
        return infos;
    }
}
