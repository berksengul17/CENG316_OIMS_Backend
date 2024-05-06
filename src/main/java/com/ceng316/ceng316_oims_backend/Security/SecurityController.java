package com.ceng316.ceng316_oims_backend.Security;

import com.ceng316.ceng316_oims_backend.Company.Company;
import com.ceng316.ceng316_oims_backend.Company.CompanyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/security")

public class SecurityController {
    private final SecurityService securityService;
    private final CompanyService companyService;

    @GetMapping("/company/changePassword")
    public RedirectView showChangePasswordPage(Model model,
                                                    @RequestParam("token") String token) {

        String result = securityService.validatePasswordResetToken(token);

        if(result != null) {
            String message = "";
            switch(result) {
                case "disabled":
                    message = "Your account is disabled please check your mail and click on the confirmation link.";

                case "expired":
                    message = "Your token has expired. Please request a new token again.";

                case "invalidUser":
                    message = "This username is invalid, or does not exist.";

                case "invalidToken":
                    message = "Invalid token.";
            }
            return new RedirectView("http://localhost:3000/reset-password");
        } else {
            model.addAttribute("token", token);
            return new RedirectView("http://localhost:3000/set-new-password");
        }
    }

    @PostMapping("/company/savePassword")
    public ResponseEntity<?> savePassword(@Valid PasswordDto passwordDto) {

        String result = securityService.validatePasswordResetToken(passwordDto.getToken());

        if(result != null) {
            String message = "Success";
            switch(result) {
                case "disabled":
                    message = "Your account is disabled please check your mail and click on the confirmation link.";

                case "expired":
                    message = "Your token has expired. Please request a new token again.";

                case "invalidUser":
                    message = "This username is invalid, or does not exist.";

                case "invalidToken":
                    message = "Invalid token.";
            }
            return ResponseEntity.ok(message);
        }

        Optional<Company> company = companyService.getCompanyByPasswordResetToken(passwordDto.getToken());
        if(company.isPresent()) {
            companyService.changeCompanyPassword(company.get(), passwordDto.getNewPassword());
            return ResponseEntity.ok("Password reset is done successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Company does not exist.");
        }
    }
}
