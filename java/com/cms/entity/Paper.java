package com.cms.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "paper")
public class Paper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paper_id")
    private int id;

    @ManyToMany
    @JoinColumn(name = "user_id", nullable = false)
    private List<User> users = new ArrayList<>();

    @Column(name = "paper_title")
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @Column(name = "paper_file")
    @Lob
    private byte[] paperFile;

    @Column(name = "abstract_file")
    @Lob
    private byte[] abstracFile;

    @Column(name = "paper_score")
    private Integer score;

    @OneToMany(mappedBy = "paper", cascade = CascadeType.ALL)
    private List<Feedback> feedbacks = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public byte[] getPaperFile() {
        return paperFile;
    }

    public void setPaperFile(byte[] paperFile) {
        this.paperFile = paperFile;
    }

    public byte[] getAbstracFile() {
        return abstracFile;
    }

    public void setAbstracFile(byte[] abstracFile) {
        this.abstracFile = abstracFile;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

}
