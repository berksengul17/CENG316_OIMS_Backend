package com.ceng316.ceng316_oims_backend.Security;

import com.ceng316.ceng316_oims_backend.Company.Company;
import com.ceng316.ceng316_oims_backend.Company.CompanyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/security")

public class SecurityController {
    private final SecurityService securityService;
    private final CompanyService companyService;

    @GetMapping("/user/changePassword")
    public ResponseEntity<?> showChangePasswordPage(Model model,
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
            return ResponseEntity.ok("redirect:/login.html?message=" + message);
        } else {
            model.addAttribute("token", token);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("redirect:/updatePassword.html");
        }
    }

    @PostMapping("/user/savePassword")
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
