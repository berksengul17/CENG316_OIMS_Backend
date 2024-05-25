package com.ceng316.ceng316_oims_backend.Feedback.IztechUserFeedback;

import com.ceng316.ceng316_oims_backend.Feedback.Feedback;
import com.ceng316.ceng316_oims_backend.Feedback.FeedbackType;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
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
public class IztechUserFeedback extends Feedback {
    @ManyToOne
    @JoinColumn(name = "iztech_user_id", referencedColumnName = "id")
    private IztechUser iztechUser;

    public IztechUserFeedback(String content, IztechUser iztechUser) {
        super(content);
        this.iztechUser = iztechUser;
    }
}