package com.ceng316.ceng316_oims_backend.DepartmentSecretary;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/department-secretary")
@AllArgsConstructor
public class DepartmentSecretaryController {

    private final DepartmentSecretaryService departmentSecretaryService;

    @PostMapping("/upload-ssi-certificate")
    public ResponseEntity<String> uploadSSICertificate(@RequestParam String studentEmail,
                                                       @RequestParam MultipartFile file) {
        try {
            departmentSecretaryService.uploadSSICertificate(studentEmail, file);
            return ResponseEntity.ok("SSI certificate uploaded successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while creating the file: " + e.getMessage());
        }
    }
}
