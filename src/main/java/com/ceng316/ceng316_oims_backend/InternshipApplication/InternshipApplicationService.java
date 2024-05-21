package com.ceng316.ceng316_oims_backend.InternshipApplication;

import com.ceng316.ceng316_oims_backend.Announcements.Announcement;
import com.ceng316.ceng316_oims_backend.Company.Company;
import com.ceng316.ceng316_oims_backend.Company.CompanyRepository;
import com.ceng316.ceng316_oims_backend.Documents.Document;
import com.ceng316.ceng316_oims_backend.Documents.DocumentService;
import com.ceng316.ceng316_oims_backend.Documents.DocumentType;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUserRepository;
import com.ceng316.ceng316_oims_backend.MailSender.MailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class InternshipApplicationService {

    private final InternshipApplicationRepository internshipApplicationRepository;
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

        internshipApplicationRepository.save(application);
        mailSenderService.sendInternshipApplicationEmail(application);

        return application;
    }

    public InternshipApplication createInternshipApplication(IztechUser student, Company company) throws IOException {
        Document applicationLetter =
                documentService.prepareDocument(student, DocumentType.APPLICATION_LETTER_TEMPLATE);

        InternshipApplication application = new InternshipApplication(student, company, applicationLetter);

        internshipApplicationRepository.save(application);
        mailSenderService.sendInternshipApplicationEmail(application);

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

        InternshipApplication application = internshipApplicationRepository
                                                .findByStudentAndCompany(student, company);

        application.setApplicationForm(form);
        internshipApplicationRepository.save(application);
    }

    public Map<String,Document> getApplicationForm(Long companyId, String studentEmail) {
        IztechUser student = iztechUserRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new IllegalArgumentException("Student with email " +
                        studentEmail + " does not exist."));
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company with id " +
                        companyId + " does not exist."));

        InternshipApplication application = internshipApplicationRepository
                                                .findByStudentAndCompany(student, company);

        return new HashMap<>(Map.of(student.getFullName() + " Application Form",
                                    application.getApplicationForm()));
    }
}
