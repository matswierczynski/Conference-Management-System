package com.cms.model;

import org.springframework.web.multipart.MultipartFile;

public class FeedbackUploadModel {
    private MultipartFile feedbackFile;
    private int score;

    public MultipartFile getFeedbackFile() {
        return feedbackFile;
    }

    public void setFeedbackFile(MultipartFile feedbackFile) {
        this.feedbackFile = feedbackFile;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
