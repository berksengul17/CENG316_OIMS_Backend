package com.ceng316.ceng316_oims_backend.Company;

import com.ceng316.ceng316_oims_backend.InternshipApplication.InternshipApplication;
import com.ceng316.ceng316_oims_backend.InternshipRegistration.InternshipRegistration;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUserService;
import com.ceng316.ceng316_oims_backend.MailSender.MailSenderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final MailSenderService mailSenderService;
    private final IztechUserService iztechUserService;

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
            if (loggedInCompany != null) {
                return ResponseEntity.ok(loggedInCompany);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("error", "Email or password is wrong"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
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
        try {
            mailSenderService.sendResetTokenEmail(request, token, company);
            companyService.createPasswordResetTokenForCompany(company, token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email.");
        }
        return ResponseEntity.ok("You should receive a password reset email shortly");
    }

    @GetMapping("/list")
    public ResponseEntity<List<Company>> listCompanies() {
        return ResponseEntity.ok(companyService.getCompanies());
    }

    @GetMapping("/approvedList")
    public ResponseEntity<List<Company>> listApprovedCompanies() {
        return ResponseEntity.ok(companyService.getApprovedCompanies());
    }

    @GetMapping("/interns/{id}")
    public ResponseEntity<?> getInterns(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(companyService.getInterns(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/intern/{email}/{companyId}")
    public ResponseEntity<?> updateStudentCompanyOwner(@PathVariable String email, @PathVariable Long companyId) {
        try {
            InternshipApplication internshipApplication = iztechUserService.updateStudentCompanyOwner(email, companyId);
            return ResponseEntity.ok(internshipApplication);
        } catch (Exception e)  {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/changeInformation/{companyId}")
    public ResponseEntity<?> updateCompanyMail(@PathVariable Long companyId, @RequestParam String email, @RequestParam String name) {
        try {
            Company company = companyService.updateCompanyNameAndMail(email, name, companyId);
            return ResponseEntity.ok(company);
        } catch (Exception e)  {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{companyId}/applied-internships")
    public ResponseEntity<?> getCompanyApplicants(@PathVariable Long companyId) {
        try {
            List<InternshipApplication> appliedInternships = companyService.getCompanyApplications(companyId);
            return ResponseEntity.ok(appliedInternships);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/applicant/{id}/approve")
    public InternshipRegistration approveApplicant(@PathVariable Long id) {
        return companyService.approveApplicant(id);
    }

    @PutMapping("/applicant/{id}/disapprove")
    public InternshipApplication disapproveApplicant(@PathVariable Long id) {
        return companyService.disapproveApplicant(id);
    }


}


