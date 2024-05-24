package com.ceng316.ceng316_oims_backend.InternshipApplication;

import com.ceng316.ceng316_oims_backend.Announcements.Announcement;
import com.ceng316.ceng316_oims_backend.Company.Company;
import com.ceng316.ceng316_oims_backend.Company.CompanyRepository;
import com.ceng316.ceng316_oims_backend.Documents.Document;
import com.ceng316.ceng316_oims_backend.Documents.DocumentService;
import com.ceng316.ceng316_oims_backend.Documents.DocumentType;
import com.ceng316.ceng316_oims_backend.InternshipRegistration.InternshipRegistration;
import com.ceng316.ceng316_oims_backend.InternshipRegistration.InternshipRegistrationRepository;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUserRepository;
import com.ceng316.ceng316_oims_backend.MailSender.MailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class InternshipApplicationService {

    private final InternshipApplicationRepository internshipApplicationRepository;
    private final InternshipRegistrationRepository internshipRegistrationRepository;
    private final IztechUserRepository iztechUserRepository;
    private final CompanyRepository companyRepository;
    private final DocumentService documentService;
    private final MailSenderService mailSenderService;

    public List<InternshipApplication> getApplicationsByStudentId(Long studentId) {
        if (!iztechUserRepository.existsById(studentId)) {
            throw new IllegalStateException("Student with id " + studentId + " does not exist.");
        }

        return internshipApplicationRepository.findAllByStudentId(studentId);
    }

    public InternshipApplication createInternshipApplication(IztechUser student, Announcement announcement) throws IOException {
        Document applicationLetter =
                documentService.prepareDocument(student, DocumentType.APPLICATION_LETTER_TEMPLATE);

        InternshipApplication application = new InternshipApplication(student, announcement, applicationLetter);

        try {
            mailSenderService.sendInternshipApplicationEmail(application);
            internshipApplicationRepository.save(application);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to send email.");
        }

        return application;
    }

    public void uploadApplicationForm(Long companyId, String studentEmail,
                               MultipartFile file) throws IOException {
        IztechUser student = iztechUserRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new IllegalArgumentException("Student with email " +
                        studentEmail + " does not exist."));
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company with id " +
                                                                companyId + " does not exist."));
        Document form = documentService.createDocument(file, DocumentType.APPLICATION_FORM);

        InternshipRegistration application = internshipRegistrationRepository
                                                .findByStudentAndCompany(student, company);

        application.setApplicationForm(form);
        internshipRegistrationRepository.save(application);
    }

    public Map<String,Document> getApplicationForm(Long companyId, String studentEmail) {
        IztechUser student = iztechUserRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new IllegalArgumentException("Student with email " +
                        studentEmail + " does not exist."));
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company with id " +
                        companyId + " does not exist."));

        InternshipRegistration application = internshipRegistrationRepository
                                                .findByStudentAndCompany(student, company);

        return new HashMap<>(Map.of(student.getFullName() + " Application Form",
                                    application.getApplicationForm()));
    }

    // TODO bu ya silince ya da düzeltilcek
//    public List<IztechUser> getInternshipApplicationsByCompany(Long companyId) {
//        List<InternshipApplication> applications = new ArrayList<>();
//        applications.addAll(internshipApplicationRepository.findByCompanyIdUsingAnnouncement(companyId));
//
//        return applications.stream()
//                .filter(application -> application.getStatus() == InternshipApplicationStatus.ACCEPTED)
//                .map(InternshipApplication::getStudent).toList();
//    }
    @Transactional
    public List<InternshipApplication> getApplicationsByCompanyId(Long companyId) {
        if (!companyRepository.existsById(companyId)) {
            throw new IllegalStateException("Company with id " + companyId + " does not exist.");
        }

        return internshipApplicationRepository.findByCompanyIdAndApplicationStatusUsingAnnouncement(companyId, InternshipApplicationStatus.PENDING)
                .orElseThrow(()-> new IllegalArgumentException("Application not found by company Id"));
    }

    public InternshipApplication approveApplicant(Long id) {
        InternshipApplication internshipApplication = internshipApplicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Internship Application not found while approving applicant"));

        internshipApplication.setStatus(InternshipApplicationStatus.ACCEPTED);
        return internshipApplicationRepository.save(internshipApplication);
    }

    public InternshipApplication disapproveApplicant(Long id) {
        InternshipApplication internshipApplication = internshipApplicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Internship Application not found while approving applicant"));

        internshipApplication.setStatus(InternshipApplicationStatus.REJECTED);
        return internshipApplicationRepository.save(internshipApplication);
    }
}
