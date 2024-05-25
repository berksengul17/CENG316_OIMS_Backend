package com.ceng316.ceng316_oims_backend.SPC;

import com.ceng316.ceng316_oims_backend.Documents.Document;
import com.ceng316.ceng316_oims_backend.InternshipRegistration.InternshipRegistration;
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
    public Document approveDocument(@PathVariable Long id,
                                    @RequestParam String studentEmail,
                                    @RequestParam int isEligible) {
        return spcService.approveDocument(id, studentEmail, isEligible);
    }
    @PutMapping("/document/{id}/disapprove")
    public Document disapproveDocument(@PathVariable Long id) {
        return spcService.disapproveDocument(id);
    }

    @GetMapping("/application-forms")
    public ResponseEntity<List<InternshipRegistration>> getApplicationForms() {
        return ResponseEntity.ok(spcService.getApplicationForms());
    }
}
