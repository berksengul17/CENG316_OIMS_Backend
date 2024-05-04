package com.ceng316.ceng316_oims_backend.Documents;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class DocumentService{

    private final DocumentRepository documentRepository;

    public Document approveDocument(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Document not found"));

        document.setStatus(DocumentStatus.APPROVED);
        return documentRepository.save(document);
    }

    public Document disapproveDocument(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Document not found"));

        document.setStatus(DocumentStatus.REJECTED);
        return documentRepository.save(document);
    }
}
