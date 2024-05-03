package com.ceng316.ceng316_oims_backend.Announcements;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/announcements")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createAnnouncement(@RequestParam("title") String title,
                                                     @RequestParam("file") MultipartFile file,
                                                     @RequestParam("publishDate") LocalDate publishDate,
                                                     @RequestParam("deadline") LocalDate deadline) {
        try {
            Announcement announcement = announcementService.createAnnouncement(title, file, publishDate, deadline);
            return ResponseEntity.ok("Announcement with ID " + announcement.getAnnouncementId() + " created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
