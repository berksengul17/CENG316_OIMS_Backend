package com.ceng316.ceng316_oims_backend.Feedback;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String content;
    protected Integer isSeen = 0;
    protected FeedbackType feedbackType;

    public Feedback(String content,FeedbackType feedbackType) {
        this.content = content;
        this.feedbackType = feedbackType;
    }
}
