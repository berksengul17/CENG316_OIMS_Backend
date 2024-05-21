package com.ceng316.ceng316_oims_backend.SPC;

import com.ceng316.ceng316_oims_backend.Documents.Document;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
