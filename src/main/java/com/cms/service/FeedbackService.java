package com.cms.service;

import org.springframework.web.multipart.MultipartFile;

import com.cms.entity.Feedback;
import com.cms.entity.Paper;
import com.cms.entity.User;

public interface FeedbackService {
    public void saveFeedback(User user, Paper paper, int score, MultipartFile feedbackFile);

    public boolean isAlreadyReviewed(User user, Paper paper);

    public Feedback findByPaperAndReviewer(User user, Paper paper);
}
