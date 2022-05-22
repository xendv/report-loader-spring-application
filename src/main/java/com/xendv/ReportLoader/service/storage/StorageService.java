package com.xendv.ReportLoader.service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    void init();

    String storeInTemp(MultipartFile file);

    Path load(String filename);

    void delete(String filename);
}
