package com.ceng316.ceng316_oims_backend.Feedback.IztechUserFeedback;

import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IztechUserFeedbackRepository extends JpaRepository<IztechUserFeedback, Long> {
    List<IztechUserFeedback> findAllByIztechUser(IztechUser iztechUser);
}
