package com.ceng316.ceng316_oims_backend.Company;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final JavaMailSender mailSender;
    private final Environment env;

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

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(HttpServletRequest request,
                                         @RequestParam String email) {
        Company company = companyService.findUserByEmail(email);
        if (company == null) {
            throw new IllegalArgumentException("Company with email" + email + "is not found");
        }
        String token = UUID.randomUUID().toString();
        companyService.createPasswordResetTokenForCompany(company, token);
        mailSender.send(constructResetTokenEmail(getAppUrl(request), token, company));
        return ResponseEntity.ok("You should receive a password reset email shortly");
    }

    @GetMapping("/list")
    public ResponseEntity<List<Company>> listCompanies() {
        return ResponseEntity.ok(companyService.getCompanies());
    }

    private SimpleMailMessage constructResetTokenEmail(
            String contextPath, String token, Company company) {
        String url = contextPath + "/security/company/changePassword?token=" + token;
        return constructEmail("Reset Password", "Reset password \r\n" + url, company);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             Company company) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(company.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}

