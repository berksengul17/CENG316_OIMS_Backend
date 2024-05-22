package com.ceng316.ceng316_oims_backend.InternshipRegistration;

import com.ceng316.ceng316_oims_backend.Company.Company;
import com.ceng316.ceng316_oims_backend.Documents.Document;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InternshipRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long internshipRegistrationId;
    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private IztechUser student;
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;
    @OneToOne
    @JoinColumn(name = "application_form_id", referencedColumnName = "documentId")
    private Document applicationForm;
    @OneToOne
    @JoinColumn(name = "ssi_id", referencedColumnName = "documentId")
    private Document ssiCertificate;

    public InternshipRegistration(IztechUser student, Company company, Document applicationForm) {
        this.student = student;
        this.company = company;
        this.applicationForm = applicationForm;
    }
}
