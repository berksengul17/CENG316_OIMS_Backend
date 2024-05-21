package com.ceng316.ceng316_oims_backend.Documents;

import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUserRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

    @Transactional
    public Document prepareDocument(IztechUser student, DocumentType documentType) throws IOException {
        return fillDocument(student, documentType);
    }

    @Transactional
    public Document prepareDocument(Long studentId, DocumentType documentType) throws IOException {
        IztechUser student = iztechUserRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        return fillDocument(student, documentType);
    }

    private Document fillDocument(IztechUser student, DocumentType documentType) throws IOException {
        Document templateFile = documentRepository.findByType(documentType)
                .orElseThrow(() -> new IllegalArgumentException("Template document not found."));

        InputStream input = new ByteArrayInputStream(templateFile.getContent());

        Map<Integer, String> replacements = new HashMap<>();
        replacements.put(0, student.getFullName());
        replacements.put(3, student.getGrade());
        replacements.put(4, student.getSchoolId());
        replacements.put(5, student.getIdentityNumber());
        replacements.put(6, student.getContactNumber());
        replacements.put(7, student.getEmail());

        byte[] docBytes = fillDocxTemplate(input, replacements);

        String docTypeStr = documentType.toString().replace("_TEMPLATE", "");
        DocumentType docType = DocumentType.valueOf(docTypeStr);

        return documentRepository.save(new Document(docBytes,
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                docType, DocumentStatus.PENDING));
    }
    private byte[] fillDocxTemplate(InputStream input, Map<Integer, String> replacements) throws IOException {
        XWPFDocument doc = new XWPFDocument(input);
        XWPFTable table = doc.getTables().get(0);
        for (Map.Entry<Integer, String> entry : replacements.entrySet()) {
            XWPFTableRow row = table.getRow(entry.getKey());
            XWPFTableCell cell = row.getCell(1);
            // Clear existing text in the cell
            cell.removeParagraph(0);
            XWPFParagraph paragraph = cell.addParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(entry.getValue());
            run.setFontSize(10);
        }

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            doc.write(out);
            byte[] docBytes = out.toByteArray();
            doc.close();
            return docBytes;
        }
    }

    public Document createDocument(MultipartFile file, DocumentType documentType) throws IOException {
        return documentRepository.save(new Document(
                file.getBytes(),
                file.getContentType(),
                documentType,
                DocumentStatus.PENDING
        ));
    }
}
