package com.ceng316.ceng316_oims_backend.Announcements;

import com.ceng316.ceng316_oims_backend.Documents.Document;
import com.ceng316.ceng316_oims_backend.Documents.DocumentStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    @Query("SELECT a FROM Announcement a WHERE a.document.status = :status")
    //TODO transactional niye lazım oldu araştır
    @Transactional
    List<Announcement> findByDocumentStatus(DocumentStatus status);
//    @Query("SELECT a FROM Announcement a WHERE a.company.id = :companyId AND a.document.status = :status")
//    //TODO transactional niye lazım oldu araştır
//    @Transactional
//    List<Announcement> findByCompanyIdAndDocumentStatus(Long companyId, DocumentStatus status);

    @Transactional
    List<Announcement> findByCompanyId(Long companyId);
}


