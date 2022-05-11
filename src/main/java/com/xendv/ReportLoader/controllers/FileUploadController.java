package com.xendv.ReportLoader.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xendv.ReportLoader.exception.storage.StorageFileNotFoundException;
import com.xendv.ReportLoader.model.FullInfo;
import com.xendv.ReportLoader.service.data.FullInfoService;
import com.xendv.ReportLoader.service.processing.DataExtractionService;
import com.xendv.ReportLoader.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public FileUploadController(StorageService storageService, DataExtractionService extractionService, FullInfoService fullInfoService) {
        this.storageService = storageService;
        this.extractionService = extractionService;
        this.fullInfoService = fullInfoService;
    }

    @PostMapping("")
    @ResponseBody
    public String handleFileUpload(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            return null;//ResponseEntity.badRequest().body("Your file is empty");
        } else {
            String newFile = storageService.storeInTemp(file);
            //var companies = extractionService.extract(newFile);
            var companies = extractionService.extract(newFile);
            //ResponseEntity.ok().body(extractionService.extract(newFile));
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                var c = objectMapper.writeValueAsString(companies);
                System.out.println(c);
                return c;
            } catch (Exception e) {
                return null;
            }

        /*redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");*/

        }

        //return null;//ResponseEntity.ok("File is ok");
    }

    @PostMapping("/save")
    public String saveUploadedDataToDb(@RequestBody List<FullInfo> fullInfos) {
        System.out.println("Got data: " + fullInfos);
        fullInfoService.saveAll(fullInfos);
        return ("Got data: " + fullInfos);
    }

    @GetMapping("/check")
    public String check() {
        return "CHECKED";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
