package com.ceng316.ceng316_oims_backend.Security;

import com.ceng316.ceng316_oims_backend.Company.CompanyService;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUserService;
import com.ceng316.ceng316_oims_backend.PasswordResetToken.PasswordResetToken;
import com.ceng316.ceng316_oims_backend.PasswordResetToken.PasswordResetTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@AllArgsConstructor
public class SecurityService {

    private final CompanyService companyService;
    private final IztechUserService iztechUserService;
    private final PasswordResetTokenService passwordResetTokenService;

    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetTokenService.getByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
