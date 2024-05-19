package com.ceng316.ceng316_oims_backend.Company;

import com.ceng316.ceng316_oims_backend.MailSender.MailSenderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final MailSenderService mailSenderService;

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
        try {
            Company loggedInCompany = companyService.login(request);
            if(loggedInCompany != null) {
                return ResponseEntity.ok(loggedInCompany);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Email or password is wrong");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(HttpServletRequest request,
                                         @RequestParam String email) {
        Company company = companyService.findUserByEmail(email);
        if (company == null) {
            throw new IllegalArgumentException("Company with email" + email + "is not found");
        }
        String token = UUID.randomUUID().toString();
        companyService.createPasswordResetTokenForCompany(company, token);
        mailSenderService.sendResetTokenEmail(request, token, company);
        return ResponseEntity.ok("You should receive a password reset email shortly");
    }

    @GetMapping("/list")
    public ResponseEntity<List<Company>> listCompanies() {
        return ResponseEntity.ok(companyService.getCompanies());
    }
}

