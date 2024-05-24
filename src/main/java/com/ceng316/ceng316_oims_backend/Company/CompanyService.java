package com.ceng316.ceng316_oims_backend.Company;

import com.ceng316.ceng316_oims_backend.InternshipApplication.InternshipApplication;
import com.ceng316.ceng316_oims_backend.InternshipApplication.InternshipApplicationService;
import com.ceng316.ceng316_oims_backend.InternshipRegistration.InternshipRegistrationService;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import com.ceng316.ceng316_oims_backend.PasswordResetToken.PasswordResetToken;
import com.ceng316.ceng316_oims_backend.PasswordResetToken.PasswordResetTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final InternshipRegistrationService internshipRegistrationService;
    private final InternshipApplicationService internshipApplicationService;

    public Company signUp(Company company) {
        boolean isEmailTaken = companyRepository.findByEmail(company.getEmail()).isPresent();
        boolean isNameTaken = companyRepository.findByCompanyName(company.getCompanyName()).isPresent();

        if (isEmailTaken) {
            throw new IllegalArgumentException("Email is already taken");
        } else if(isNameTaken) {
            throw new IllegalArgumentException("Name is already taken");
        }else if (!isValidEmail(company.getEmail())) {
            throw new IllegalArgumentException("Invalid email address");
        } else if (company.getCompanyName().length() > 30 || company.getCompanyName().length() < 2) {
            throw new IllegalArgumentException("Company name should be between 2 and 30 characters");
        }

        company.setRegistrationStatus(RegistrationStatus.PENDING);
        return companyRepository.save(company);
    }

    public Company login(Company companyCredentials) {
        Company company = companyRepository.findByEmail(companyCredentials.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        if (company.getPassword().equals(companyCredentials.getPassword())) {
            return new Company(company.getId(), company.getEmail(), company.getCompanyName(), company.getRegistrationStatus());
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

    public void banCompany (Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
        companyRepository.delete(company);

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
        if (passwordResetTokenRepository.findByCompany(company).isPresent()) {
            passwordResetTokenRepository.deleteByCompany(company);
        }

        passwordResetTokenRepository.save(new PasswordResetToken(token, company));
    }
    public void changeCompanyPassword(Company company, String password) {
        company.setPassword(password);
        companyRepository.save(company);
    }
    public Optional<Company> getCompanyByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getCompany());
    }

    public List<Company> getCompanies() {
        return companyRepository.findByRegistrationStatus(RegistrationStatus.PENDING);
    }

    public List<Company> getApprovedCompanies() {
        return companyRepository.findByRegistrationStatus(RegistrationStatus.APPROVED);
    }

    @Transactional
    public List<IztechUser> getInterns(Long companyId) {
        return internshipRegistrationService.getInternsByCompany(companyId);
    }

    public Company updateCompanyNameAndMail(String companyMail, String name, Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        boolean isEmailTaken = companyRepository.findByEmail(companyMail).isPresent();
        boolean isNameTaken = companyRepository.findByCompanyName(name).isPresent();

        if (!company.getEmail().equals(companyMail) && isEmailTaken) {
            throw new IllegalArgumentException("Email is already taken");
        } else if(!company.getCompanyName().equals(name)  && isNameTaken) {
            throw new IllegalArgumentException("Name is already taken");
        } else if (!isValidEmail(company.getEmail())) {
            throw new IllegalArgumentException("Invalid email address");
        } else if (company.getCompanyName().length() > 30 || company.getCompanyName().length() < 2) {
            throw new IllegalArgumentException("Company name should be between 2 and 30 characters");
        }

        company.setEmail(companyMail);
        company.setCompanyName(name);
        return companyRepository.save(company);
    }

    private boolean isValidEmail(String emailAddress) {
        return Pattern.compile("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
                .matcher(emailAddress)
                .matches();
    }
    @Transactional
    public List<InternshipApplication> getCompanyApplications(Long companyId) {
        return internshipApplicationService.getApplicationsByCompanyId(companyId);
    }

    public InternshipApplication approveApplicant(@PathVariable Long id) {
        return internshipApplicationService.approveApplicant(id);
    }

    public InternshipApplication disapproveApplicant(@PathVariable Long id) {
        return internshipApplicationService.disapproveApplicant(id);
    }
}
