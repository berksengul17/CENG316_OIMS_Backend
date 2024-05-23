package com.ceng316.ceng316_oims_backend.Announcements;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
                                                     @RequestParam("deadline") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deadline,
                                                     @RequestParam("companyId") Long companyId) {
        try {
            Announcement announcement = announcementService.createAnnouncement(title, file, deadline, companyId);
            return ResponseEntity.ok("Announcement with ID " + announcement.getAnnouncementId() + " created successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Can't read file: " + e.getMessage());
        }
    }

    @PutMapping("/update/{announcementId}")
    public ResponseEntity<?> updateAnnouncement(@PathVariable Long announcementId, @RequestParam(value = "title", required = false) String title,
                                                @RequestParam(value = "file", required = false) MultipartFile file,
                                                @RequestParam(value = "deadline", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deadline)  {
        try {
            Announcement announcement = announcementService.updateAnnouncement(announcementId, title, file, deadline);
            return ResponseEntity.ok("Announcement with ID " + announcement.getAnnouncementId() + " is updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadAnnouncement(@PathVariable String id) {
        Announcement announcement = announcementService.getAnnouncement(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(announcement.getDocument().getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + announcement.getTitle() + "\"")
                .body(announcement.getDocument().getContent());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Announcement>> getPendingAnnouncements() {
        return ResponseEntity.ok(announcementService.getPendingAnnouncements());
    }
    @GetMapping("/approved")
    public ResponseEntity<List<Announcement>> getApprovedAnnouncements() {
        return ResponseEntity.ok(announcementService.getApprovedAnnouncements());
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<Announcement>> getAnnouncementsForCompany(@PathVariable Long id) {
        return ResponseEntity.ok(announcementService.getAnnouncementsForCompany(id));
    }
    @PutMapping("/delete/{id}")
    public void deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
    }

}
