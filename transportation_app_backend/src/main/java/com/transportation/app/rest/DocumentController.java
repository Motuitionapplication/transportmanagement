package com.transportation.app.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.transportation.app.binding.Document;
import com.transportation.app.repo.DocumentRepository;
import com.transportation.app.service.FileStorageService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/files")
public class DocumentController {

    @Autowired
    private FileStorageService storageService;

    @Autowired
    private DocumentRepository documentRepository;

    /**
     * Upload any document (PDF, image, etc.) and save metadata in MySQL
     */
    @PostMapping("/upload-document")
    public ResponseEntity<?> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") String userId) {
        try {
            // 1. Store file on disk
            String path = storageService.storeFile(file, userId);

            // 2. Save metadata to database
            Document doc = new Document();
            doc.setUserId(userId);
            doc.setFilename(file.getOriginalFilename());
            doc.setFileType(file.getContentType());
            doc.setFilePath(path);
            documentRepository.save(doc);

            // 3. Return success response including DB record ID
            return ResponseEntity.ok(Map.of(
                "message",     "Document uploaded successfully",
                "path",        path,
                "documentId",  doc.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "error",   "Upload failed",
                "details", e.getMessage()
            ));
        }
    }

    /**
     * Upload passport photo (same logic as document upload)
     */
    @PostMapping("/upload-passport-photo")
    public ResponseEntity<?> uploadPassportPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") String userId) {
        return uploadDocument(file, userId);
    }
}
