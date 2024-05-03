package com.ceng316.ceng316_oims_backend.Student;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor

public class StudentController {
    private final StudentService studentService;
}
