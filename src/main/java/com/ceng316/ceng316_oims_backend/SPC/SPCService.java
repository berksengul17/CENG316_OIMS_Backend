package com.ceng316.ceng316_oims_backend.SPC;

import com.ceng316.ceng316_oims_backend.Documents.Document;
import com.ceng316.ceng316_oims_backend.Documents.DocumentService;
import com.ceng316.ceng316_oims_backend.InternshipRegistration.InternshipRegistration;
import com.ceng316.ceng316_oims_backend.InternshipRegistration.InternshipRegistrationRepository;
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

    public Document approveDocument(@PathVariable Long id) {
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
            if (registration.getApplicationForm() != null) {
                registrationsWithFOrms.add(registration);
            }
        }

        return registrationsWithFOrms;
    }

}
