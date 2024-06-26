package com.ceng316.ceng316_oims_backend.InternshipApplication;

import com.ceng316.ceng316_oims_backend.Announcements.Announcement;
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

    @Query("SELECT ia FROM InternshipApplication ia " +
            "JOIN ia.student s " +
            "JOIN ia.announcement a " +
            "JOIN a.company c " +
            "WHERE s.email = :email " +
            "AND c.id = :companyId")
    Optional<InternshipApplication> findByStudentEmailAndCompanyId(@Param("email") String email, @Param("companyId") Long companyId);
//    Optional<InternshipApplication> findByStudentIdAndCompanyId(Long studentId, Long companyId);

    // TODO ya silincek ya da düzeltilcek
    @Query("SELECT ia FROM InternshipApplication ia " +
            "JOIN ia.announcement a " +
            "JOIN a.company c " +
            "WHERE c.id = :companyId")
    @Transactional
    Optional<List<InternshipApplication>> findByCompanyIdUsingAnnouncement(@Param("companyId") Long companyId);

    @Query("SELECT ia FROM InternshipApplication ia " +
            "JOIN ia.announcement a " +
            "JOIN a.company c " +
            "WHERE c.id = :companyId AND ia.status = :status")
    Optional<List<InternshipApplication>> findByCompanyIdAndStatusUsingAnnouncement(
            @Param("companyId") Long companyId,
            @Param("status") InternshipApplicationStatus status);
    InternshipApplication findByStudentAndAnnouncement(IztechUser student, Announcement announcement);

}
