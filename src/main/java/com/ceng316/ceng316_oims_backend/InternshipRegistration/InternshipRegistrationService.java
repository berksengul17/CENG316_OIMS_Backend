package com.ceng316.ceng316_oims_backend.InternshipRegistration;

import com.ceng316.ceng316_oims_backend.Company.Company;
import com.ceng316.ceng316_oims_backend.Company.CompanyRepository;
import com.ceng316.ceng316_oims_backend.Documents.Document;
import com.ceng316.ceng316_oims_backend.Documents.DocumentService;
import com.ceng316.ceng316_oims_backend.Documents.DocumentType;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUserRepository;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class InternshipRegistrationService {
    private final InternshipRegistrationRepository internshipRegistrationRepository;
    private final IztechUserRepository iztechUserRepository;
    private final CompanyRepository companyRepository;
    private final DocumentService documentService;

    public InternshipRegistration registerToCompany(Long studentId, String companyEmail) throws IOException {
        IztechUser student = iztechUserRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        Company company = companyRepository.findByEmail(companyEmail)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        Document form = documentService.prepareDocument(student, DocumentType.APPLICATION_FORM_TEMPLATE);

        InternshipRegistration internshipRegistration = new InternshipRegistration(student, company, form);

        return internshipRegistrationRepository.save(internshipRegistration);
    }

    public List<IztechUser> getInternsByCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        List<InternshipRegistration> registrations = internshipRegistrationRepository.findAllByCompany(company);

        return registrations.stream().map(InternshipRegistration::getStudent).toList();
    }

}
