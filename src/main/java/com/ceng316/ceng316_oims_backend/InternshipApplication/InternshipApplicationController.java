package com.ceng316.ceng316_oims_backend.InternshipApplication;

import com.ceng316.ceng316_oims_backend.Documents.Document;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/internship-applications")
@AllArgsConstructor
public class InternshipApplicationController {

    private final InternshipApplicationService internshipApplicationService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getApplicationsByStudentId(@PathVariable Long studentId) {
        try {
            return ResponseEntity.ok(internshipApplicationService.getApplicationsByStudentId(studentId));
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/upload-application-form")
    public ResponseEntity<String> uploadApplicationForm(@RequestParam Long companyId,
                                                        @RequestParam String studentEmail,
                                                        @RequestParam MultipartFile file) {
        try {
            internshipApplicationService.uploadApplicationForm(companyId, studentEmail, file);
            return ResponseEntity.ok("Application form uploaded successfully.");
        } catch (IllegalArgumentException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/download-application-form")
    public ResponseEntity<?> downloadApplicationForm(@RequestParam Long companyId,
                                                     @RequestParam String studentEmail) {
        try {
            Map<String, Document> form = internshipApplicationService.getApplicationForm(companyId, studentEmail);
            String fileName = form.keySet().iterator().next();
            Document document = form.get(fileName);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(document.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(document.getContent());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
