package com.ceng316.ceng316_oims_backend.SPC;

import com.ceng316.ceng316_oims_backend.Documents.Document;
import com.ceng316.ceng316_oims_backend.Documents.DocumentService;
import com.ceng316.ceng316_oims_backend.Documents.DocumentStatus;
import com.ceng316.ceng316_oims_backend.InternshipRegistration.InternshipRegistration;
import com.ceng316.ceng316_oims_backend.InternshipRegistration.InternshipRegistrationRepository;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class SPCService {

    private final DocumentService documentService;
    private final InternshipRegistrationRepository internshipRegistrationRepository;
    private final IztechUserRepository iztechUserRepository;

    public Document approveDocument(Long id, String studentEmail, int isEligible) {
        IztechUser student = iztechUserRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        student.setIsEligible(isEligible);
        iztechUserRepository.save(student);

        return documentService.approveDocument(id);
    }

    public Document disapproveDocument(@PathVariable Long id) {
        return documentService.disapproveDocument(id);
    }

    @Transactional
    public List<InternshipRegistration> getApplicationForms() {
        List<InternshipRegistration> registrationsWithFOrms = new ArrayList<>();

        List<InternshipRegistration> allRegistrations = internshipRegistrationRepository.findAll();
        for (InternshipRegistration registration : allRegistrations) {
            if (registration.getApplicationForm() != null && registration.getApplicationForm().getStatus() == DocumentStatus.PENDING) {
                registrationsWithFOrms.add(registration);
            }
        }

        return registrationsWithFOrms;
    }

}
