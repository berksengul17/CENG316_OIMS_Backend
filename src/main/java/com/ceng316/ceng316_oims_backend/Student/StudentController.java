package com.ceng316.ceng316_oims_backend.Student;

import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/students")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/eligible-students")
    public ResponseEntity<List<IztechUser>> getEligibleStudents() {
        return ResponseEntity.ok(studentService.getEligibleStudents());
    }

    @GetMapping("/get-eligible-students-pdf")
    public ResponseEntity<?> getEligibleStudentsPdf() {
        try {
            byte[] eligibleStudentsPdf = studentService.createEligibleStudentsPdf();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                    ContentDisposition.builder("attachment")
                            .filename("eligible_students.pdf")
                            .build());
            return new ResponseEntity<>(eligibleStudentsPdf, headers, HttpStatus.OK);
        } catch (DocumentException e ) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Document error:" + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while creating file:" + e.getMessage());
        }
    }

    @PostMapping("/{studentId}/apply/{announcementId}")
    public ResponseEntity<?> applyToAnnouncement(@PathVariable Long studentId, @PathVariable Long announcementId) {
        try {
            return ResponseEntity.ok(studentService.applyToAnnouncement(studentId, announcementId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while filling document: " + e.getMessage());
        }
    }
}
