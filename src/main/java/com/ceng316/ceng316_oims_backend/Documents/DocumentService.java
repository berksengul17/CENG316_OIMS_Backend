package com.ceng316.ceng316_oims_backend.Documents;

import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUserRepository;
import com.ceng316.ceng316_oims_backend.Student.StudentService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.AllArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor

public class DocumentService {

    private final DocumentRepository documentRepository;
    private final IztechUserRepository iztechUserRepository;
    private final StudentService studentService;

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

    public void createEligibleStudentsPdf() throws IOException, DocumentException {
        List<IztechUser> eligibleStudents = studentService.getEligibleStudents();

        File file = new File("src/main/resources/static/Eligible_Students.pdf");
        if (file.createNewFile()) {
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));

            BaseFont baseFont = BaseFont.createFont("src/main/resources/static/OpenSans-Regular.ttf",
                                                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 12);

            document.open();

            PdfPTable table = new PdfPTable(6);
            addTableHeader(table, font);
            addRows(table, eligibleStudents, font);

            document.add(table);
            document.close();
        }
    }

    public void fillDocument(Long userId, DocumentType documentType) throws Exception {
        IztechUser user = iztechUserRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Optional<Document> templateFile = documentRepository.findByType(documentType);

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


    private void addTableHeader(PdfPTable table, Font font) {
        Stream.of("ID", "Full Name", "E-mail", "Grade", "Contact Number", "Identity Number")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle, font));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, List<IztechUser> eligibleStudents, Font font) {
        for (IztechUser student : eligibleStudents) {
            table.addCell(new Phrase(String.valueOf(student.getId()), font));
            table.addCell(new Phrase(student.getFullName(), font));
            table.addCell(new Phrase(student.getEmail(), font));
            table.addCell(new Phrase(student.getGrade(), font));
            table.addCell(new Phrase(student.getContactNumber(), font));
            table.addCell(new Phrase(student.getIdentityNumber(), font));
        }
    }
}
