package com.ceng316.ceng316_oims_backend.DepartmentSecretary;

import com.ceng316.ceng316_oims_backend.Documents.Document;
import com.ceng316.ceng316_oims_backend.Documents.DocumentService;
import com.ceng316.ceng316_oims_backend.Documents.DocumentType;
import com.ceng316.ceng316_oims_backend.InternshipRegistration.InternshipRegistration;
import com.ceng316.ceng316_oims_backend.InternshipRegistration.InternshipRegistrationRepository;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class DepartmentSecretaryService {

    private final DocumentService documentService;
    private final IztechUserRepository iztechUserRepository;
    private final InternshipRegistrationRepository internshipRegistrationRepository;

    @Transactional
    public void uploadSSICertificate(String studentEmail, MultipartFile file) throws IOException {
        IztechUser student = iztechUserRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        Document ssi = documentService.createDocument(file, DocumentType.SSI_DOCUMENT);

        InternshipRegistration registration = internshipRegistrationRepository.findByStudent(student);
        if (registration == null) {
            throw new IllegalArgumentException("Student is not registered to any internship");
        }

        registration.setSsiCertificate(ssi);

        internshipRegistrationRepository.save(registration);
    }
}
