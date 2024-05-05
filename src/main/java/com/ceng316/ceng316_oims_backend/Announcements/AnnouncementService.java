package com.ceng316.ceng316_oims_backend.Announcements;

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

@Service
@AllArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final DocumentRepository documentRepository;

    @Transactional
    public Announcement createAnnouncement(String title, MultipartFile file,
                                           LocalDate deadline) throws IOException {
        Document document = new Document(
                file.getBytes(),
                file.getContentType(),
                DocumentType.ANNOUNCEMENT,
                DocumentStatus.PENDING
        );

        Announcement announcement = new Announcement(title, deadline, document);
        documentRepository.save(document);
        return announcementRepository.save(announcement);
    }

    public Announcement getAnnouncement(String id) {
        return announcementRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found"));
    }
}
