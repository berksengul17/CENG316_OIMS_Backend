package com.ceng316.ceng316_oims_backend.InternshipApplication;

import com.ceng316.ceng316_oims_backend.IztechUser.IztechUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InternshipApplicationService {

    private final InternshipApplicationRepository internshipApplicationRepository;
    private final IztechUserRepository iztechUserRepository;

    public List<InternshipApplication> getApplicationsByStudentId(Long studentId) {
        if (!iztechUserRepository.existsById(studentId)) {
            throw new IllegalStateException("Student with id " + studentId + " does not exist.");
        }

        return internshipApplicationRepository.findAllByStudentId(studentId);
    }
}
