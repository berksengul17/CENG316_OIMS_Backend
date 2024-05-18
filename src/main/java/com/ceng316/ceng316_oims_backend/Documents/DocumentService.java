package com.ceng316.ceng316_oims_backend.Documents;


import lombok.AllArgsConstructor;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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

        document.setStatus(DocumentStatus.DISAPPROVED);
        return documentRepository.save(document);
    }

    public void fillTemplateWithStudentData(String templatePath, String outputPath, Student student) throws IOException {
        try (XWPFDocument doc = new XWPFDocument(new FileInputStream(templatePath))) {
            for (XWPFTable table : doc.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        fillCellWithStudentData(cell, student);
                    }
                }
            }

            try (FileOutputStream out = new FileOutputStream(outputPath)) {
                doc.write(out);
            }
        }
    }

    private void fillCellWithStudentData(XWPFTableCell cell, Student student) {
        String cellText = cell.getText();
        if (cellText.contains("ADI - SOYADI")) {
            appendTextToCell(cell, student.getFullName());
        } else if (cellText.contains("SINIFI")) {
            appendTextToCell(cell, student.getClassYear());
        } else if (cellText.contains("OKUL NUMARASI")) {
            appendTextToCell(cell, student.getSchoolNumber());
        } else if (cellText.contains("T.C. KİMLİK NO")) {
            appendTextToCell(cell, student.getNationalId());
        } else if (cellText.contains("CEP TELEFONU")) {
            appendTextToCell(cell, student.getPhoneNumber());
        } else if (cellText.contains("E-POSTA")) {
            appendTextToCell(cell, student.getEmail());
        }
    }

    private void appendTextToCell(XWPFTableCell cell, String textToAppend) {
        XWPFParagraph paragraph;
        if (cell.getParagraphs().isEmpty()) {
            paragraph = cell.addParagraph();
        } else {
            paragraph = cell.getParagraphs().get(0);
        }
        XWPFRun run = paragraph.createRun();
        run.setText(textToAppend);
    }
    public byte[] getDocumentTemplateFromDB() {
        // Assuming you have a JDBC template or similar setup
        String sql = "SELECT doc_template FROM templates WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{templateId}, (ResultSet rs, int rowNum) -> {
            Blob docBlob = rs.getBlob("doc_template");
            int blobLength = (int) docBlob.length();
            byte[] blobAsBytes = docBlob.getBytes(1, blobLength);
            docBlob.free();
            return blobAsBytes;
        });
    }
}

