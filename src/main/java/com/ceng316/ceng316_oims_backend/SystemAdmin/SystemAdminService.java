package com.ceng316.ceng316_oims_backend.SystemAdmin;

import com.ceng316.ceng316_oims_backend.Company.Company;
import com.ceng316.ceng316_oims_backend.Company.CompanyService;
import com.ceng316.ceng316_oims_backend.Documents.Document;
import com.ceng316.ceng316_oims_backend.Documents.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@AllArgsConstructor
public class SystemAdminService {

    private final DocumentService documentService;
    private final CompanyService companyService;

    public Document approveDocument(@PathVariable Long id) {
        return documentService.approveDocument(id);
    }

    public Document disapproveDocument(@PathVariable Long id) {
        return documentService.disapproveDocument(id);
    }

    public Company approveCompany(@PathVariable Long id) {
        return companyService.approveCompany(id);
    }

    public Company disapproveCompany(@PathVariable Long id) {
        return companyService.disapproveCompany(id);
    }
}
