package com.xendv.ReportLoader.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xendv.ReportLoader.exception.storage.StorageFileNotFoundException;
import com.xendv.ReportLoader.service.processing.DataExtractionService;
import com.xendv.ReportLoader.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rest/api")
@CrossOrigin(origins = "*")
public class FileUploadController {

    @Autowired
    private final StorageService storageService;

    @Autowired
    private final DataExtractionService extractionService;

    @Autowired
    public FileUploadController(StorageService storageService, DataExtractionService extractionService) {
        this.storageService = storageService;
        this.extractionService = extractionService;
    }

    /*@GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }*/

    @PostMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            return null;//ResponseEntity.badRequest().body("Your file is empty");
        } else {
            String newFile = storageService.storeInTemp(file);
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

    @GetMapping("/check")
    public String check() {
        return "CHECKED";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
