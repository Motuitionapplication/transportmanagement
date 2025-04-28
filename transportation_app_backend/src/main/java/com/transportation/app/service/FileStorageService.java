package com.transportation.app.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
public class FileStorageService {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    private Path rootLocation;

    @PostConstruct
    public void init() throws IOException {
        this.rootLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        if (Files.notExists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }
    }

    /**
     * Store the given MultipartFile on disk under userId_timestamp_originalName.
     * @return full path of stored file
     */
    public String storeFile(MultipartFile file, String userId) throws IOException {
        String original = StringUtils.cleanPath(file.getOriginalFilename());
        String stored = userId + "_" + System.currentTimeMillis() + "_" + original;
        Path target = this.rootLocation.resolve(stored);
        // overwrite if exists
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return target.toString();
    }
}
