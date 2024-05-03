package com.ceng316.ceng316_oims_backend.Announcements;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/announcements")

public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PutMapping("/{id}/approve")
    public Announcement approveAnnouncement(@PathVariable Long id) {
        return announcementService.approveAnnouncement(id);
    }

    @PutMapping("/{id}/disapprove")
    public Announcement disapproveAnnouncement(@PathVariable Long id) {
        return announcementService.disapproveAnnouncement(id);
    }

}
