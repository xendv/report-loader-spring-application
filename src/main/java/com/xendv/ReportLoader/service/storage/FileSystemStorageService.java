package com.xendv.ReportLoader.service.storage;

import com.xendv.ReportLoader.exception.storage.StorageException;
import com.xendv.ReportLoader.exception.storage.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    public String storeInTemp(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Выбранный файл пустой. Пожалуйста, выберите другой");
            }
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(Objects.requireNonNull(file.getOriginalFilename()))
                    )
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("Невозможно сохранить выбранный файл вне директории");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                return destinationFile.toString();
            }
        } catch (IOException e) {
            throw new StorageException("Невозможно сохранить файл", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public File loadAsFile(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource.getFile();
            }
            else {
                throw new StorageFileNotFoundException(
                        "Ошибка чтения файла: " + filename);

            }
        } catch (IOException e) {
            throw new StorageFileNotFoundException("Ошибка чтения файла: " + filename, e);
        }
    }

    public void delete(String filename) {
        try {
            FileSystemUtils.deleteRecursively(rootLocation.resolve(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Не удалось проинициализировать временное хранилище", e);
        }
    }
}
