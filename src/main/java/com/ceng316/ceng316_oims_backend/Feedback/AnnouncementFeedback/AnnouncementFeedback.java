package com.ceng316.ceng316_oims_backend.Feedback.AnnouncementFeedback;

import com.ceng316.ceng316_oims_backend.Announcements.Announcement;
import com.ceng316.ceng316_oims_backend.Feedback.Feedback;
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
public class AnnouncementFeedback extends Feedback {
    @ManyToOne
    @JoinColumn(name = "announcement_id", referencedColumnName = "announcementId")
    private Announcement announcement;

    public AnnouncementFeedback(String content, Announcement announcement) {
        super(content);
        this.announcement = announcement;
    }
}