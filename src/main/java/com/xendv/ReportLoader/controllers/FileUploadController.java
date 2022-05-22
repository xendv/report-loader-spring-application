package com.xendv.ReportLoader.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xendv.ReportLoader.model.FullInfo;
import com.xendv.ReportLoader.service.data.FullInfoService;
import com.xendv.ReportLoader.service.processing.DataExtractionService;
import com.xendv.ReportLoader.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/api/upload")
@CrossOrigin(origins = "http://localhost:3000")
public class FileUploadController {

    @Autowired
    private final StorageService storageService;

    @Autowired
    private final DataExtractionService extractionService;

    @Autowired
    private final FullInfoService fullInfoService;

    @Autowired
    public FileUploadController(@NotNull StorageService storageService,
                                @NotNull DataExtractionService extractionService,
                                @NotNull FullInfoService fullInfoService) {
        this.storageService = storageService;
        this.extractionService = extractionService;
        this.fullInfoService = fullInfoService;
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<String> handleFileUpload(@RequestParam(value = "file") MultipartFile file) {
        String newFile = storageService.storeInTemp(file);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            var companies = extractionService.extract(newFile);
            var companiesJsonString = objectMapper.writeValueAsString(companies);

            var companiesAndStates = fullInfoService.getStates(companies);
            List<String> states = new ArrayList<>(companiesAndStates.values());
            var statesJsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(states);

            Map<String, String> responseMap = new LinkedHashMap<>();
            responseMap.put("infos", companiesJsonString);
            responseMap.put("states", statesJsonString);

            var responseJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseMap);
            System.out.println("Extracted data in JSON: \n" + responseJson);
            return ResponseEntity.ok().body(responseJson);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }

    @PostMapping("/save")
    public String saveUploadedDataToDb(@RequestBody List<FullInfo> fullInfos) {
        fullInfoService.saveAll(fullInfos);
        return ("Saved data: " + fullInfos);
    }
}
