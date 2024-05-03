package com.ceng316.ceng316_oims_backend.Documents;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class DocumentService{

    private final DocumentRepository documentRepository;

}
