package com.ceng316.ceng316_oims_backend.Feedback;

import com.ceng316.ceng316_oims_backend.Announcements.Announcement;
import com.ceng316.ceng316_oims_backend.Announcements.AnnouncementRepository;
import com.ceng316.ceng316_oims_backend.Company.Company;
import com.ceng316.ceng316_oims_backend.Company.CompanyRepository;
import com.ceng316.ceng316_oims_backend.Feedback.AnnouncementFeedback.AnnouncementFeedback;
import com.ceng316.ceng316_oims_backend.Feedback.AnnouncementFeedback.AnnouncementFeedbackRepository;
import com.ceng316.ceng316_oims_backend.Feedback.CompanyFeedback.CompanyFeedback;
import com.ceng316.ceng316_oims_backend.Feedback.CompanyFeedback.CompanyFeedbackRepository;
import com.ceng316.ceng316_oims_backend.Feedback.IztechUserFeedback.IztechUserFeedback;
import com.ceng316.ceng316_oims_backend.Feedback.IztechUserFeedback.IztechUserFeedbackRepository;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FeedbackService {
    private final AnnouncementFeedbackRepository announcementFeedbackRepository;
    private final CompanyFeedbackRepository companyFeedbackRepository;
    private final IztechUserFeedbackRepository iztechUserFeedbackRepository;
    private final AnnouncementRepository announcementRepository;
    private final CompanyRepository companyRepository;
    private final IztechUserRepository iztechUserRepository;

    public AnnouncementFeedback addAnnouncementFeedback(Long announcementId, String content){
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found"));
        return announcementFeedbackRepository.save(new AnnouncementFeedback(content, announcement));
    }

    public CompanyFeedback addCompanyFeedback(Long companyId, String content){
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
        return companyFeedbackRepository.save(new CompanyFeedback(content, company));
    }

    public IztechUserFeedback addIztechUserFeedback(Long iztechUserId, String content){
        IztechUser iztechUser = iztechUserRepository.findById(iztechUserId)
                .orElseThrow(() -> new IllegalArgumentException("Iztech User not found"));
        return iztechUserFeedbackRepository.save(new IztechUserFeedback(content, iztechUser));
    }
}
