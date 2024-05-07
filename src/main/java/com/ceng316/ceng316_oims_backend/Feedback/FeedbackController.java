package com.ceng316.ceng316_oims_backend.Feedback;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
@AllArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;

    @PostMapping("/company/{companyId}")
    public ResponseEntity<?> addCompanyFeedback(@PathVariable Long companyId, @RequestBody Feedback feedback) {
        try {
            return ResponseEntity.ok(feedbackService.addCompanyFeedback(companyId, feedback.getContent()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/iztech-user/{userId}")
    public ResponseEntity<?> addUserFeedback(@PathVariable Long userId, @RequestBody Feedback feedback) {
        try {
            return ResponseEntity.ok(feedbackService.addIztechUserFeedback(userId, feedback.getContent()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/announcement/{announcementId}")
    public ResponseEntity<?> addAnnouncementFeedback(@PathVariable Long announcementId, @RequestBody Feedback feedback) {
        try {
            return ResponseEntity.ok(feedbackService.addAnnouncementFeedback(announcementId, feedback.getContent()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
