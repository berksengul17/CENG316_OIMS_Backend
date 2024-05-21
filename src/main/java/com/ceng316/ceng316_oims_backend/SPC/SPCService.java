package com.ceng316.ceng316_oims_backend.SPC;

import com.ceng316.ceng316_oims_backend.Documents.Document;
import com.ceng316.ceng316_oims_backend.Documents.DocumentService;
import com.ceng316.ceng316_oims_backend.InternshipApplication.InternshipApplication;
import com.ceng316.ceng316_oims_backend.InternshipApplication.InternshipApplicationRepository;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
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
    private final InternshipApplicationRepository internshipApplicationRepository;

    public Document approveDocument(@PathVariable Long id) {
        return documentService.approveDocument(id);
    }

    public Document disapproveDocument(@PathVariable Long id) {
        return documentService.disapproveDocument(id);
    }

    @Transactional
    public List<InternshipApplication> getApplicationForms() {
        List<InternshipApplication> applicationsWithForm = new ArrayList<>();

        List<InternshipApplication> allApplications = internshipApplicationRepository.findAll();
        for (InternshipApplication application : allApplications) {
            if (application.getApplicationForm() != null) {
                applicationsWithForm.add(application);
            }
        }

        return applicationsWithForm;
    }

}
