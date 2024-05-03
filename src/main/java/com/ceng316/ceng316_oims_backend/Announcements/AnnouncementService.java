package com.ceng316.ceng316_oims_backend.Announcements;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public Announcement approveAnnouncement(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found"));

        announcement.setStatus(AnnouncementStatus.APPROVED);
        return announcementRepository.save(announcement);
    }

    public Announcement disapproveAnnouncement(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found"));

        announcement.setStatus(AnnouncementStatus.REJECTED);
        return announcementRepository.save(announcement);
    }
}

