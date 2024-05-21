package com.ceng316.ceng316_oims_backend.InternshipApplication;

import com.ceng316.ceng316_oims_backend.Company.Company;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternshipApplicationRepository extends JpaRepository<InternshipApplication, Long> {
    @Transactional
    List<InternshipApplication> findAllByStudentId(Long studentId);
    @Transactional
    InternshipApplication findByStudentAndCompany(IztechUser student, Company company);
    Optional<InternshipApplication> findByStudentIdAndCompanyId(Long studentId, Long companyId);
    List<InternshipApplication> findByCompanyId(Long id);
    @Query("SELECT ia FROM InternshipApplication ia " +
            "JOIN ia.student s " +
            "JOIN ia.announcement a " +
            "JOIN a.company c " +
            "WHERE s.email = :email " +
            "AND c.id = :companyId")
    Optional<InternshipApplication> findByStudentEmailAndCompanyId(@Param("email") String email, @Param("companyId") Long companyId);

    @Query("SELECT ia FROM InternshipApplication ia " +
            "JOIN ia.announcement a " +
            "JOIN a.company c " +
            "WHERE c.id = :companyId")
    List<InternshipApplication> findByCompanyIdUsingAnnouncement(@Param("companyId") Long companyId);
}
