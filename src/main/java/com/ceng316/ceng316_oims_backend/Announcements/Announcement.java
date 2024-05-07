package com.ceng316.ceng316_oims_backend.Announcements;

import com.ceng316.ceng316_oims_backend.Company.Company;
import com.ceng316.ceng316_oims_backend.Documents.Document;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;

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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id", referencedColumnName = "documentId")
    private Document document;
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id") // This is the foreign key column in the Announcement table.
    private Company company;
    public Announcement(String title, LocalDate deadline, Document document, Company company) {
        this.title = title;
        this.publishDate = LocalDate.now(ZoneId.of("Europe/Istanbul"));
        this.deadline = deadline;
        this.document = document;
        this.company = company;
    }
}
