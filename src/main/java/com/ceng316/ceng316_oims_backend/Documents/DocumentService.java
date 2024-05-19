package com.ceng316.ceng316_oims_backend.Documents;

import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUserRepository;
import com.ceng316.ceng316_oims_backend.Student.StudentService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.AllArgsConstructor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @Transactional
    public void fillDocument(Long userId, DocumentType documentType) throws Exception {
        IztechUser user = iztechUserRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Document templateFile = documentRepository.findByType(documentType)
                .orElseThrow(() -> new IllegalArgumentException("Template document not found."));

        InputStream input = new ByteArrayInputStream(templateFile.getContent());

        Map<Integer, String> replacements = new HashMap<>();
        replacements.put(0, user.getFullName());
        replacements.put(3, user.getGrade());
        replacements.put(4, user.getSchoolId());
        replacements.put(5, user.getIdentityNumber());
        replacements.put(6, user.getContactNumber());
        replacements.put(7, user.getEmail());

        if (isOle2Format(templateFile.getContent())) {
            fillDocTemplate(input, replacements,
                    "src/main/resources/static/Filled_Document_" + documentType + ".doc");
        } else {
            fillDocxTemplate(input, replacements,
                    "src/main/resources/static/Filled_Document_" + documentType + ".docx");
        }
    }

    private boolean isOle2Format(byte[] content) {
        // Simple check to determine if the content is OLE2 format
        // OLE2 format files start with specific bytes (D0 CF 11 E0 A1 B1 1A E1)
        return content.length > 8 && content[0] == (byte) 0xD0 && content[1] == (byte) 0xCF;
    }

    private void fillDocTemplate(InputStream input, Map<Integer, String> replacements, String outputFilePath) throws IOException {
        HWPFDocument doc = new HWPFDocument(input);
        Range range = doc.getRange();
        TableIterator itr = new TableIterator(range);
        if (itr.hasNext()) {
            Table table = itr.next();
            for (Map.Entry<Integer, String> entry : replacements.entrySet()) {
                TableRow row = table.getRow(entry.getKey());
                TableCell cell = row.getCell(1);
                Paragraph paragraph = cell.getParagraph(0);
                paragraph.replaceText(entry.getValue(), false);
            }
        }

        try (FileOutputStream out = new FileOutputStream(outputFilePath)) {
            doc.write(out);
        }
    }

    private void fillDocxTemplate(InputStream input, Map<Integer, String> replacements, String outputFilePath) throws IOException {
        XWPFDocument doc = new XWPFDocument(input);
        XWPFTable table = doc.getTables().get(0);
        for (Map.Entry<Integer, String> entry : replacements.entrySet()) {
            XWPFTableRow row = table.getRow(entry.getKey());
            XWPFTableCell cell = row.getCell(1);
            cell.setText(entry.getValue());
        }

        try (FileOutputStream out = new FileOutputStream(outputFilePath)) {
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

    public Document createDocument(MultipartFile file, DocumentType documentType) throws IOException {
        return documentRepository.save(new Document(
                file.getBytes(),
                file.getContentType(),
                documentType,
                DocumentStatus.APPROVED
        ));
    }
}
