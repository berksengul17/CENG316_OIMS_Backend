package com.ceng316.ceng316_oims_backend.SPC;

import com.ceng316.ceng316_oims_backend.Documents.Document;
import com.ceng316.ceng316_oims_backend.Documents.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@AllArgsConstructor
@Service
public class SPCService {

    private final DocumentService documentService;

    public Document approveDocument(@PathVariable Long id) {
        return documentService.approveDocument(id);
    }

    public Document disapproveDocument(@PathVariable Long id) {
        return documentService.disapproveDocument(id);
    }

}
