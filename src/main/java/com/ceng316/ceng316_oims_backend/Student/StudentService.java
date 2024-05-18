package com.ceng316.ceng316_oims_backend.Student;

import com.ceng316.ceng316_oims_backend.InternshipApplication.InternshipApplication;
import com.ceng316.ceng316_oims_backend.InternshipApplication.InternshipApplicationRepository;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUserRepository;
import com.ceng316.ceng316_oims_backend.IztechUser.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class StudentService {

    private final IztechUserRepository iztechUserRepository;

    public List<IztechUser> getEligibleStudents() {
        Optional<List<IztechUser>> students = iztechUserRepository.findByRole(Role.STUDENT);
        return students.map(iztechUsers -> iztechUsers
                .stream()
                .filter(student -> student.getIsEligible() == 1)
                .toList()).orElseGet(List::of);

    }
}
