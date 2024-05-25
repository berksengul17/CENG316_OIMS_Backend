package com.ceng316.ceng316_oims_backend.Feedback.CompanyFeedback;

import com.ceng316.ceng316_oims_backend.Company.Company;
import com.ceng316.ceng316_oims_backend.Feedback.Feedback;
import com.ceng316.ceng316_oims_backend.Feedback.FeedbackType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CompanyFeedback extends Feedback {
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    public CompanyFeedback(String content, Company company) {
        super(content);
        this.company = company;
    }
}
