package com.ceng316.ceng316_oims_backend.InternshipApplication;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
