package com.ceng316.ceng316_oims_backend.Feedback.CompanyFeedback;

import com.ceng316.ceng316_oims_backend.Company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyFeedbackRepository extends JpaRepository<CompanyFeedback, Long> {
    List<CompanyFeedback> findAllByCompanyAndIsSeen(Company company, int isSeen);
}
