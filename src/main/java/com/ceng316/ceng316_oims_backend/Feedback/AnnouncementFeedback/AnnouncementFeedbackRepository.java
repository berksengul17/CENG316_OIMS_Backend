package com.ceng316.ceng316_oims_backend.Feedback.AnnouncementFeedback;

import com.ceng316.ceng316_oims_backend.Announcements.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementFeedbackRepository extends JpaRepository<AnnouncementFeedback, Long> {
    List<AnnouncementFeedback> findAllByAnnouncement(Announcement announcement);
}
