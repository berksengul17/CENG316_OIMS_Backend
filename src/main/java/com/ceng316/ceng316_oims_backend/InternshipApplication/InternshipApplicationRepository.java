package com.ceng316.ceng316_oims_backend.InternshipApplication;

import com.ceng316.ceng316_oims_backend.Company.Company;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternshipApplicationRepository extends JpaRepository<InternshipApplication, Long> {
    List<InternshipApplication> findAllByStudentId(Long studentId);
    @Transactional
    InternshipApplication findByStudentAndCompany(IztechUser student, Company company);
    Optional<InternshipApplication> findByStudentIdAndCompanyId(Long studentId, Long companyId);

    List<InternshipApplication> findByCompanyId(Long id);

}
