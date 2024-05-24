package com.ceng316.ceng316_oims_backend.InternshipRegistration;

import com.ceng316.ceng316_oims_backend.Announcements.Announcement;
import com.ceng316.ceng316_oims_backend.Announcements.AnnouncementRepository;
import com.ceng316.ceng316_oims_backend.Company.Company;
import com.ceng316.ceng316_oims_backend.Company.CompanyRepository;
import com.ceng316.ceng316_oims_backend.Documents.Document;
import com.ceng316.ceng316_oims_backend.Documents.DocumentService;
import com.ceng316.ceng316_oims_backend.Documents.DocumentType;
import com.ceng316.ceng316_oims_backend.InternshipApplication.InternshipApplicationRepository;
import com.ceng316.ceng316_oims_backend.InternshipApplication.InternshipApplicationService;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class InternshipRegistrationService {
    private final InternshipRegistrationRepository internshipRegistrationRepository;
    private final InternshipApplicationRepository internshipApplicationRepository;
    private final InternshipApplicationService internshipApplicationService;
    private final IztechUserRepository iztechUserRepository;
    private final CompanyRepository companyRepository;
    private final AnnouncementRepository announcementRepository;
    private final DocumentService documentService;

    public InternshipRegistration createInternshipRegistration(IztechUser student, Company company) {
        return internshipRegistrationRepository.save(new InternshipRegistration(student, company));
    }

    @Transactional
    public InternshipRegistration registerToCompany(Long studentId,
                                                    Long announcementId) throws IOException {
        IztechUser student = iztechUserRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found"));

        InternshipRegistration registration = internshipRegistrationRepository
                                                .findByStudentAndCompany(student, announcement.getCompany());

        // prepare document
        registration.setApplicationForm(documentService
                                        .prepareDocument(student, DocumentType.APPLICATION_FORM_TEMPLATE));
        // mark as registered
        registration.setStatus(InternshipRegistrationStatus.ACCEPTED);
        // save to db and return
        registration = internshipRegistrationRepository.save(registration);

        rejectOtherPendingCompanies(student);

        return registration;
    }

    public List<InternshipRegistration> getInternsByCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        return internshipRegistrationRepository.findAllByCompany(company);
    }

    @Transactional
    public Document getSSIByStudentId(Long studentId) {
        IztechUser student = iztechUserRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        InternshipRegistration registration = internshipRegistrationRepository.findByStudent(student);

        return registration.getSsiCertificate();
    }


    private void rejectOtherPendingCompanies(IztechUser student) {
        List<InternshipRegistration> registrations = internshipRegistrationRepository.findAllByStudent(student);
        for (InternshipRegistration registration : registrations) {
            if (registration.getStatus() == InternshipRegistrationStatus.PENDING) {
                registration.setStatus(InternshipRegistrationStatus.REJECTED);
                internshipRegistrationRepository.save(registration);
            }
        }
    }
}
