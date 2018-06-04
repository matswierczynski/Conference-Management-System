package com.cms.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cms.entity.Paper;
import com.cms.entity.User;

public interface PaperService {

    public Paper findPaperById(int id);

    public Paper findPaperByTitle(String title);

    public List<Paper> findAllByOrderByTitleAsc();

    public void setStatus(Paper paper, String status);

    public void savePaper(Paper paper);

    public void savePaper(String title, MultipartFile paper, MultipartFile abstr, User user);

    public int getNumberOfReviewers(Paper paper);

    public List<User> getReviewers(Paper paper);

    public void addUser(Paper paper, User user);

    public int getNumberOfFeedbacks(Paper paper);

    public void setTotalScore(Paper paper);

    public void changeStatus(Paper paper);

}
