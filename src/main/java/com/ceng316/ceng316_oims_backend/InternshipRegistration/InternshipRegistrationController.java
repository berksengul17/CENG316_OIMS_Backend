package com.ceng316.ceng316_oims_backend.InternshipRegistration;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/internship-registration")
@AllArgsConstructor
public class InternshipRegistrationController {

    private final InternshipRegistrationService internshipRegistrationService;

    // TODO Request param burda iyi mi bilmiyorum
    @PostMapping("/register")
    public ResponseEntity<?> registerToCompany(@RequestParam Long studentId, @RequestParam String companyEmail) {
        try {
            InternshipRegistration newRegistration = internshipRegistrationService.registerToCompany(studentId, companyEmail);
            return ResponseEntity.ok(newRegistration);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error while filling the document: " + e.getMessage());
        }
    }
}
