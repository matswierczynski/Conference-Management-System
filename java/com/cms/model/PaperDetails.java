package com.cms.model;

public class PaperDetails {
    private String paperTitle;
    private Integer score;
    private Integer reviewersNumber;
    private String status;

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getReviewersNumber() {
        return reviewersNumber;
    }

    public void setReviewersNumber(Integer reviewersNumber) {
        this.reviewersNumber = reviewersNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
