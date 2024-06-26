package com.ceng316.ceng316_oims_backend.Student;

import com.ceng316.ceng316_oims_backend.Announcements.Announcement;
import com.ceng316.ceng316_oims_backend.Announcements.AnnouncementRepository;
import com.ceng316.ceng316_oims_backend.Company.CompanyRepository;
import com.ceng316.ceng316_oims_backend.InternshipApplication.InternshipApplication;
import com.ceng316.ceng316_oims_backend.InternshipApplication.InternshipApplicationService;
import com.ceng316.ceng316_oims_backend.InternshipRegistration.InternshipRegistration;
import com.ceng316.ceng316_oims_backend.InternshipRegistration.InternshipRegistrationRepository;
import com.ceng316.ceng316_oims_backend.InternshipRegistration.InternshipRegistrationService;
import com.ceng316.ceng316_oims_backend.InternshipRegistration.InternshipRegistrationStatus;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUserRepository;
import com.ceng316.ceng316_oims_backend.IztechUser.Role;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor

public class StudentService {

    private final IztechUserRepository iztechUserRepository;
    private final AnnouncementRepository announcementRepository;
    private final InternshipApplicationService internshipApplicationService;
    private final InternshipRegistrationRepository internshipRegistrationRepository;

    public List<IztechUser> getEligibleStudents() {
        List<InternshipRegistration> registrations = internshipRegistrationRepository.findAll();

        return registrations.stream()
                .filter(registration -> (registration.getStatus() == InternshipRegistrationStatus.ACCEPTED) &&
                                            registration.getStudent().getIsEligible() == 1)
                .map(InternshipRegistration::getStudent)
                .toList();
    }

    public List<InternshipApplication> getAppliedInternships(Long studentId) {
        return internshipApplicationService.getApplicationsByStudentId(studentId);
    }

    public byte[] createEligibleStudentsPdf() throws IOException, DocumentException {
        List<IztechUser> eligibleStudents = getEligibleStudents();

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, byteArrayOutputStream);

        ClassPathResource resource = new ClassPathResource("static/OpenSans-Regular.ttf");
        BaseFont baseFont = null;
        try (InputStream inputStream = resource.getInputStream()) {
            baseFont = BaseFont.createFont(
                    "OpenSans-Regular.ttf",
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED,
                    false,
                    inputStream.readAllBytes(),
                    null
            );
            // Use the baseFont as needed
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Font font = new Font(baseFont, 12);

        document.open();

        PdfPTable table = new PdfPTable(5);
        addTableHeader(table, font);
        addRows(table, eligibleStudents, font);

        document.add(table);
        document.close();

        return byteArrayOutputStream.toByteArray();
    }

    public InternshipApplication applyToAnnouncement(Long studentId, Long announcementId) throws IOException {
        IztechUser student = iztechUserRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("Announcement with id " + announcementId + "not found"));

        return internshipApplicationService.createInternshipApplication(student, announcement);
    }

    private void addTableHeader(PdfPTable table, Font font) {
        Stream.of("Full Name", "E-mail", "Grade", "Contact Number", "Identity Number")
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
            table.addCell(new Phrase(student.getFullName(), font));
            table.addCell(new Phrase(student.getEmail(), font));
            table.addCell(new Phrase(student.getGrade(), font));
            table.addCell(new Phrase(student.getContactNumber(), font));
            table.addCell(new Phrase(student.getIdentityNumber(), font));
        }
    }

}
