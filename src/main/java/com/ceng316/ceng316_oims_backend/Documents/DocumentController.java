package com.ceng316.ceng316_oims_backend.Documents;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService documentService;


//    @PutMapping("/{id}/approve")
//    public Document approveDocument(@PathVariable Long id) {
//        return DocumentService.approveDocument(id);
//    }
//
//    @PutMapping("/{id}/disapprove")
//    public Document disapproveDocument(@PathVariable Long id) {
//        return DocumentService.disapproveDocument(id);
//    }

}
