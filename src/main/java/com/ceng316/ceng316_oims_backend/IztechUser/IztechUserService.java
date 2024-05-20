package com.ceng316.ceng316_oims_backend.IztechUser;

import com.ceng316.ceng316_oims_backend.Company.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class IztechUserService {

    private final IztechUserRepository iztechUserRepository;
    private final CompanyRepository companyRepository;

    public IztechUser login(IztechUser iztechUserCredentials) {
        Optional<IztechUser> optional_iztech_user = Optional.empty();
        if (iztechUserCredentials.getEmail() != null) {
            optional_iztech_user = iztechUserRepository.findByEmail(iztechUserCredentials.getEmail());
        } else {
            optional_iztech_user = iztechUserRepository.findById(iztechUserCredentials.getId());
        }

        if (optional_iztech_user.isPresent() && optional_iztech_user.get().getPassword().equals(iztechUserCredentials.getPassword())) {
            IztechUser iztechUserInfo = optional_iztech_user.get();
            return new IztechUser(iztechUserInfo.getId(), iztechUserInfo.getFullName(), iztechUserInfo.getEmail(), iztechUserInfo.getRole(),
                    iztechUserInfo.getIdentityNumber(), iztechUserInfo.getSchoolId(), iztechUserInfo.getGrade(), iztechUserInfo.getContactNumber());
        }

        return null;
    }
    public List<IztechUser> getStudents(Long id) {
        return iztechUserRepository.findByCompanyId(id);
    }

    public IztechUser updateStudentCompanyOwner(String email, Long companyId) {
        IztechUser student = iztechUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        student.setCompany(companyRepository.findById(companyId).
                orElseThrow(() -> new IllegalArgumentException("Company not found")));  // assuming there's a setCompanyId method
        return iztechUserRepository.save(student);
        }
    }

