package com.ceng316.ceng316_oims_backend.SystemAdmin;

import com.ceng316.ceng316_oims_backend.Company.Company;
import com.ceng316.ceng316_oims_backend.Documents.Document;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/systemadmin")

public class SystemAdminController {

    private final SystemAdminService systemAdminService;

    @PutMapping("/document/{id}/approve")
    public Document approveDocument(@PathVariable Long id) {
        return systemAdminService.approveDocument(id);
    }

    @PutMapping("/document/{id}/disapprove")
    public Document disapproveDocument(@PathVariable Long id) {
        return systemAdminService.disapproveDocument(id);
    }

    @PutMapping("/company/{id}/approve")
    public Company approveCompany(@PathVariable Long id) {
        return systemAdminService.approveCompany(id);
    }

    @PutMapping("/company/{id}/disapprove")
    public Company disapproveCompany(@PathVariable Long id) {
        return systemAdminService.disapproveCompany(id);
    }

    @PutMapping("/company/{id}/ban")
    public Company banCompany(@PathVariable Long id) {
        return systemAdminService.banCompany(id);
    }


}
