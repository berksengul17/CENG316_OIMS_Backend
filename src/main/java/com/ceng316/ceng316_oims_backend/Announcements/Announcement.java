package com.ceng316.ceng316_oims_backend.Announcements;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long announcementId;
    private String title;
    private LocalDate publishDate;
    private LocalDate deadline;
    @Enumerated(EnumType.STRING)
    private AnnouncementStatus status;
}
