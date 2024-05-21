package com.ceng316.ceng316_oims_backend.Announcements;

import com.ceng316.ceng316_oims_backend.Company.Company;
import com.ceng316.ceng316_oims_backend.Company.CompanyRepository;
import com.ceng316.ceng316_oims_backend.Documents.Document;
import com.ceng316.ceng316_oims_backend.Documents.DocumentRepository;
import com.ceng316.ceng316_oims_backend.Documents.DocumentStatus;
import com.ceng316.ceng316_oims_backend.Documents.DocumentType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final DocumentRepository documentRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public Announcement createAnnouncement(String title, MultipartFile file,
                                           LocalDate deadline, Long companyId) throws IOException {

        String validationError = isValidAnnouncement(title, file, deadline);
        if (validationError != null) {
            throw new IllegalArgumentException(validationError);
        }

        Document document = new Document(
                file.getBytes(),
                file.getContentType(),
                DocumentType.ANNOUNCEMENT,
                DocumentStatus.PENDING
        );

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        Announcement announcement = new Announcement(title, deadline, document, company);
        documentRepository.save(document);
        return announcementRepository.save(announcement);
    }

    //FIXME çok fazla null kontrolü var
    @Transactional
    public Announcement updateAnnouncement(Long announcementId, String title, MultipartFile file, LocalDate deadline) throws IOException {
        Announcement announcement = announcementRepository.findById((announcementId))
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found"));

        String validationError = isValidAnnouncement(title, file, deadline);
        if (validationError != null) {
            throw new IllegalArgumentException(validationError);
        }

        if (file != null) {
            Document document = announcement.getDocument();
            document.setContent(file.getBytes());
            document.setContentType(file.getContentType());
            documentRepository.save(document);
        }
        if (title != null) {
            announcement.setTitle(title);
        }
        if (deadline != null) {
            announcement.setDeadline(deadline);
        }

        return announcementRepository.save(announcement);
    }

    public Announcement getAnnouncement(String id) {
        return announcementRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found"));
    }

    public List<Announcement> getPendingAnnouncements() {
        return announcementRepository.findByDocumentStatus(DocumentStatus.PENDING);
    }

    public List<Announcement> getApprovedAnnouncements() {
        return announcementRepository.findByDocumentStatus(DocumentStatus.APPROVED)
                .stream()
                .filter(announcement -> announcement.getDeadline().isAfter(LocalDate.now()) ||
                                        announcement.getDeadline().isEqual(LocalDate.now()))
                .toList();
    }

    public List<Announcement> getAnnouncementsForCompany(Long id) {
        return announcementRepository.findByCompanyId(id);
    }

    private String isValidAnnouncement(String title, MultipartFile file, LocalDate deadline) {
        if ((title != null) && (title.length() < 3 || title.length() > 50)) {
            return "Title must be between 3 and 50 characters.";
        } else if (file != null && !Objects.equals(file.getContentType(), "application/pdf")) {
            return "File must be a PDF.";
        } else if (deadline != null && deadline.isBefore(LocalDate.now())) {
            return "Deadline can't be before the current date.";
        }

        return null;
    }

}
