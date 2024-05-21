package com.ceng316.ceng316_oims_backend.IztechUser;

import com.ceng316.ceng316_oims_backend.Announcements.AnnouncementRepository;
import com.ceng316.ceng316_oims_backend.Company.CompanyRepository;
import com.ceng316.ceng316_oims_backend.InternshipApplication.InternshipApplication;
import com.ceng316.ceng316_oims_backend.InternshipApplication.InternshipApplicationRepository;
import com.ceng316.ceng316_oims_backend.InternshipApplication.InternshipApplicationStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class IztechUserService {

    private final IztechUserRepository iztechUserRepository;
    private final CompanyRepository companyRepository;
    private final InternshipApplicationRepository internshipApplicationRepository;
    private final AnnouncementRepository announcementRepository;

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
    public List<InternshipApplication> getStudents(Long id) {
        return internshipApplicationRepository.findByCompanyId(id);
    }

    @Transactional
    public InternshipApplication updateStudentCompanyOwner(String email, Long companyId) {
        IztechUser student = iztechUserRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("Student not found"));

        Optional<InternshipApplication> internshipApplication = internshipApplicationRepository
                .findByStudentIdAndCompanyId(student.getId(), companyId);

        if (internshipApplication.isEmpty()) {
            internshipApplication = internshipApplicationRepository.findByStudentEmailAndCompanyId(email, companyId);
            if (internshipApplication.isEmpty()) {
                throw new IllegalArgumentException("Internship application not found");
            }
        }

        internshipApplication.get().setStatus(InternshipApplicationStatus.ACCEPTED);
        return internshipApplicationRepository.save(internshipApplication.get());
        }
    }

