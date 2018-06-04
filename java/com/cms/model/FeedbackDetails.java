package com.cms.model;

public class FeedbackDetails {
    String title;
    String status;
    String alreadyReviewed;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlreadyReviewed() {
        return alreadyReviewed;
    }

    public void setAlreadyReviewed(boolean alreadyReviewed) {
        if (alreadyReviewed) {
            this.alreadyReviewed = "Yes";
        } else {
            this.alreadyReviewed = "No";
        }
    }

}
