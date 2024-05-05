package com.ceng316.ceng316_oims_backend.Company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody Company request) {
        try {
            Company newCompany = companyService.signUp(request);
            return ResponseEntity.ok(newCompany.getCompanyName() + " signed up successfully");
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Company request) {
        Company loggedInCompany = companyService.login(request);
        if(loggedInCompany != null) {
            return ResponseEntity.ok(loggedInCompany);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Email or password is wrong");
        }
    }

    @PostMapping("/{id}/resetpassword")
    public ResponseEntity<?> resetPassword(@RequestBody String request, @PathVariable Long id) {
        try {
            companyService.resetPassword(id, request);
            return ResponseEntity.ok(" Password changed successfully");
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }


    }
}
