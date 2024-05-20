package com.ceng316.ceng316_oims_backend;

import com.ceng316.ceng316_oims_backend.Company.Company;
import com.ceng316.ceng316_oims_backend.Company.CompanyRepository;
import com.ceng316.ceng316_oims_backend.Company.RegistrationStatus;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUserRepository;
import com.ceng316.ceng316_oims_backend.IztechUser.Role;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final IztechUserRepository iztechUserRepository;
    private final CompanyRepository companyRepository;


    @Override
    public void run(String... args) throws Exception {
        iztechUserRepository.save(new IztechUser("Admin", "admin@admin.com", "123", Role.SYSTEM_ADMIN, "13154178426", "0555 555 55 55"));
        IztechUser student = new IztechUser("Berk Şengül", "berk@std.iyte.edu.tr", "123", Role.STUDENT, "13154178426", "0555 555 55 55", 1);
        student.setGrade("3");
        student.setSchoolId("280201015");
        iztechUserRepository.save(student);
        iztechUserRepository.save(new IztechUser("Buket Öksüzoğlu", "buket@iyte.edu.tr", "123", Role.SUMMER_PRACTICE_COORDINATOR, "13154178426", "0555 555 55 55"));
        companyRepository.save(new Company("test_approved@company.com", "123", "Test Company Approved", RegistrationStatus.APPROVED));
        companyRepository.save(new Company("test_pending@company.com", "123", "Test Company Pending", RegistrationStatus.PENDING));
        companyRepository.save(new Company("test_pending@company.com    ", "123", "Test Company Pending", RegistrationStatus.PENDING));
        companyRepository.save(new Company("test_pending2@company.com", "123", "Test Company Pending", RegistrationStatus.PENDING));
        companyRepository.save(new Company("test_pending3@company.com", "123", "Test Company Pending", RegistrationStatus.PENDING));
        companyRepository.save(new Company("test_pending4@company.com", "123", "Test Company Pending", RegistrationStatus.PENDING));
        companyRepository.save(new Company("test_pending5@company.com", "123", "Test Company Pending", RegistrationStatus.PENDING));

        companyRepository.save(new Company("test_banned@company.com", "123", "Test Company Banned", RegistrationStatus.BANNED));
    }
}
