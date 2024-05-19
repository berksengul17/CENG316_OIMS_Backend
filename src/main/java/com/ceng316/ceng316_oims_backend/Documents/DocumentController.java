package com.ceng316.ceng316_oims_backend.Documents;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService documentService;
    private final DocumentRepository documentRepository;

    //TODO buna gerek olmayabilir
    @GetMapping("/fillDocument")
    public ResponseEntity<String> fillDocument(@RequestParam Long studentId, @RequestParam DocumentType documentType) {
        try {
            documentService.prepareDocument(studentId, documentType);
            return ResponseEntity.ok("Document is filled");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("type") DocumentType documentType) {
        try {
            Document document = documentService.createDocument(file, documentType);
            return ResponseEntity.ok("Document with ID " + document.getDocumentId() + " created successfully.");
        }  catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Can't read file: " + e.getMessage());
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadAnnouncement(@PathVariable Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Document not found"));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getType() + "\"")
                .body(document.getContent());
    }



//    @PutMapping("/{id}/approve")
//    public Document approveDocument(@PathVariable Long id) {
//        return DocumentService.approveDocument(id);
//    }
//
//    @PutMapping("/{id}/disapprove")
//    public Document disapproveDocument(@PathVariable Long id) {
//        return DocumentService.disapproveDocument(id);
//    }

}
