package com.ceng316.ceng316_oims_backend.InternshipApplication;

import com.ceng316.ceng316_oims_backend.Announcements.Announcement;
import com.ceng316.ceng316_oims_backend.Documents.Document;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InternshipApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private IztechUser student;
    @ManyToOne
    @JoinColumn(name = "announcement_id", referencedColumnName = "announcementId")
    private Announcement announcement;
    @ManyToOne
    @JoinColumn(name = "application_letter_id", referencedColumnName = "documentId")
    private Document applicationLetter;
    @ManyToOne
    @JoinColumn(name="application_form_id", referencedColumnName = "documentId")
    private Document applicationForm;
    @Enumerated(EnumType.STRING)
    private InternshipApplicationStatus status;
    private LocalDate applicationDate;
}
