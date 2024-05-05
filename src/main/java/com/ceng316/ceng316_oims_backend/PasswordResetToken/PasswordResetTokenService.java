package com.ceng316.ceng316_oims_backend.PasswordResetToken;

import com.ceng316.ceng316_oims_backend.Company.Company;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetToken getByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);

    }



}
