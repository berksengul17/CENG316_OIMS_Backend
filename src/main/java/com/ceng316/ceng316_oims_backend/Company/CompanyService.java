package com.ceng316.ceng316_oims_backend.Company;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

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
}
