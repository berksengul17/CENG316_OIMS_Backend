package com.ceng316.ceng316_oims_backend.Documents;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor

public class DocumentController {

    private final DocumentService documentService;

}
