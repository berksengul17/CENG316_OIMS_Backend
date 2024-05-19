package com.ceng316.ceng316_oims_backend.InternshipApplication;

import com.ceng316.ceng316_oims_backend.Announcements.Announcement;
import com.ceng316.ceng316_oims_backend.Documents.Document;
import com.ceng316.ceng316_oims_backend.Documents.DocumentService;
import com.ceng316.ceng316_oims_backend.Documents.DocumentType;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUserRepository;
import com.ceng316.ceng316_oims_backend.MailSender.MailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class InternshipApplicationService {

    private final InternshipApplicationRepository internshipApplicationRepository;
    private final IztechUserRepository iztechUserRepository;
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
}
