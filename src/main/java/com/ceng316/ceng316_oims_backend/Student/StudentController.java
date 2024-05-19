package com.ceng316.ceng316_oims_backend.Student;

import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
