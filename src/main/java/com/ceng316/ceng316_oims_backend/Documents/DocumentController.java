package com.ceng316.ceng316_oims_backend.Documents;

import com.ceng316.ceng316_oims_backend.Announcements.Announcement;
import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService documentService;
    private final DocumentRepository documentRepository;

    @GetMapping("/createEligibleStudentsPdf")
    public ResponseEntity<String> createEligibleStudentsPdf() {
        try {
            documentService.createEligibleStudentsPdf();
            return ResponseEntity.ok("Eligible students pdf is created.");
        } catch (DocumentException e ) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Document error:" + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while creating file:" + e.getMessage());
        }
    }

    @GetMapping("/fillDocument")
    public ResponseEntity<String> fillDocument(@RequestParam Long studentId, @RequestParam DocumentType documentType) {
        try {
            documentService.fillDocument(studentId, documentType);
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
            return ResponseEntity.ok("Announcement with ID " + document.getDocumentId() + " created successfully.");
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
