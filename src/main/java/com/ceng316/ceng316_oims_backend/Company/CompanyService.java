package com.ceng316.ceng316_oims_backend.Company;

import com.ceng316.ceng316_oims_backend.PasswordResetToken.PasswordResetTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public Company signUp(Company company) {
        boolean isEmailTaken = companyRepository.findByEmail(company.getEmail()).isPresent();

        if (isEmailTaken) {
            throw new IllegalArgumentException("Email is already taken");
        }

        company.setRegistrationStatus(RegistrationStatus.PENDING);
        return companyRepository.save(company);
    }

    public Company login(Company companyCredentials) {
        Optional<Company> company = companyRepository.findByEmail(companyCredentials.getEmail());

        if (company.isPresent() && company.get().getPassword().equals(companyCredentials.getPassword())) {
            Company companyInfo = company.get();
            return new Company(companyInfo.getEmail(), companyInfo.getCompanyName(), companyInfo.getContactNumber());
        }

        return null;
    }

    public Company approveCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        company.setRegistrationStatus(RegistrationStatus.APPROVED);
        return companyRepository.save(company);
    }

    public Company disapproveCompany (Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        company.setRegistrationStatus(RegistrationStatus.DISAPPROVED);
        return companyRepository.save(company);
    }

    public void resetPassword(Long id, String request) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        company.setPassword(request);
        companyRepository.save(company);
    }

    public Company findUserByEmail(String userEmail) {
        return companyRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
    }

    public void createPasswordResetTokenForCompany(Company company, String token) {
    }
    public void changeCompanyPassword(Company company, String password) {
        company.setPassword(password);
        companyRepository.save(company);
    }
    public Optional<Company> getCompanyByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getCompany());
    }

}

