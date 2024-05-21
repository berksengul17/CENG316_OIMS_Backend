package com.ceng316.ceng316_oims_backend.SPC;

import com.ceng316.ceng316_oims_backend.Documents.Document;
import com.ceng316.ceng316_oims_backend.InternshipApplication.InternshipApplication;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/spc")
public class SPCController {

    private final SPCService spcService;

    @PutMapping("/document/{id}/approve")
    public Document approveDocument(@PathVariable Long id) {
        return spcService.approveDocument(id);
    }
    @PutMapping("/document/{id}/disapprove")
    public Document disapproveDocument(@PathVariable Long id) {
        return spcService.disapproveDocument(id);
    }

    @GetMapping("/application-forms")
    public ResponseEntity<List<InternshipApplication>> getApplicationForms() {
        return ResponseEntity.ok(spcService.getApplicationForms());
    }
}
