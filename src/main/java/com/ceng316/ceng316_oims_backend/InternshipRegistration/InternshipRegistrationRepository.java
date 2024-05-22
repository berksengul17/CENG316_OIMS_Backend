package com.ceng316.ceng316_oims_backend.InternshipRegistration;

import com.ceng316.ceng316_oims_backend.Company.Company;
import com.ceng316.ceng316_oims_backend.InternshipApplication.InternshipApplication;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InternshipRegistrationRepository extends JpaRepository<InternshipRegistration, Long> {
    List<InternshipRegistration> findAllByCompany(Company company);
    @Transactional
    InternshipRegistration findByStudentAndCompany(IztechUser student, Company company);
}