package com.ceng316.ceng316_oims_backend.PasswordResetToken;

import com.ceng316.ceng316_oims_backend.Company.Company;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);
    Optional<PasswordResetToken> findByCompany(Company company);
    @Transactional
    void deleteByCompany(Company company);
}
