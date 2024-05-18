package com.ceng316.ceng316_oims_backend.Documents;


import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUserRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor

public class DocumentService {

    private final DocumentRepository documentRepository;
    private final IztechUserRepository iztechUserRepository;

    public Document approveDocument(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Document not found"));

        document.setStatus(DocumentStatus.APPROVED);
        return documentRepository.save(document);
    }

    public Document disapproveDocument(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Document not found"));

        document.setStatus(DocumentStatus.DISAPPROVED);
        return documentRepository.save(document);
    }

    public void fillDocument(Long userId, DocumentType documentType) throws Exception {
        IztechUser user = iztechUserRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Optional<Document> templateFile = documentRepository.findByDocumentType(documentType);

        if (templateFile.isEmpty()) {
            throw new IllegalArgumentException("Template document not found");
        }

        XWPFDocument doc = new XWPFDocument(new FileInputStream(String.valueOf(templateFile)));

        // Create a map for the titles and corresponding user values
        Map<String, String> replacements = new HashMap<>();

        if (documentType == DocumentType.APPLICATION_LETTER_TEMPLATE) {
            replacements.put("ADI - SOYADI", user.getFullName());
            replacements.put("SINIFI", user.getGrade());
            replacements.put("OKUL NUMARASI", user.getSchoolId());
            replacements.put("T.C. KİMLİK NO", user.getIdentityNumber());
            replacements.put("CEP TELEFONU", user.getContactNumber());
            replacements.put("E-POSTA", user.getEmail());
        } else if (documentType == DocumentType.APPLICATION_FORM_TEMPLATE) {
            // Define replacements specific to the application form template
            replacements.put("ADI - SOYADI", user.getFullName());
            replacements.put("SINIFI", user.getGrade());
            replacements.put("OKUL NUMARASI", user.getSchoolId());
            replacements.put("T.C. KİMLİK NO", user.getIdentityNumber());
            replacements.put("""
                    CEP TELEFONU

                    (Kendisinin / Yakınının)""", user.getContactNumber());
            replacements.put("E-POSTA", user.getEmail());
        }
        // Replace placeholders in the document
        for (XWPFParagraph p : doc.getParagraphs()) {
            int runsSize = p.getRuns().size();
            for (int i = 0; i < runsSize; i++) {
                XWPFRun run = p.getRuns().get(i);
                String text = run.getText(0);
                if (text != null && replacements.containsKey(text.trim())) {
                    if (i + 1 < runsSize) {
                        XWPFRun nextRun = p.getRuns().get(i + 1);
                        nextRun.setText(replacements.get(text.trim()), 0);
                    } else {
                        // If no run exists after the title, create a new one
                        XWPFRun newRun = p.createRun();
                        newRun.setText(replacements.get(text.trim()));
                    }
                    break; // Break after setting text to avoid overwriting multiple times
                }
            }
        }

        // Save the filled document
        try (FileOutputStream out = new FileOutputStream("Filled_Document_" + documentType + ".docx")) {
            doc.write(out);
        }
    }
}
