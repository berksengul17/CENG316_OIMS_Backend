package com.ceng316.ceng316_oims_backend.Documents;

import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/createEligibleStudentsPdf")
    public ResponseEntity<String> createEligibleStudentsPdf() {
        try {
            documentService.createEligibleStudentsPdf();
            return ResponseEntity.ok("Eligible students pdf is created.");
        } catch (DocumentException e ) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Document error:" + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while creating file:" + e.getMessage());
        }
    }


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
