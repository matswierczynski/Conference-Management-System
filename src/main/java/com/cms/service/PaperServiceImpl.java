package com.cms.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cms.entity.Feedback;
import com.cms.entity.Paper;
import com.cms.entity.Status;
import com.cms.entity.User;
import com.cms.repository.FeedbackRepository;
import com.cms.repository.PaperRepository;
import com.cms.repository.StatusRepository;

@Service
@ComponentScan(basePackages = { "com.cms.repository" })
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public Paper findPaperById(int id) {
        Long lId = (long) id;
        return paperRepository.getOne(lId);
    }

    @Override
    public Paper findPaperByTitle(String title) {
        return paperRepository.findByTitle(title);
    }

    @Override
    public List<Paper> findAllByOrderByTitleAsc() {
        return paperRepository.findAllByOrderByTitleAsc();
    }

    @Override
    public void savePaper(Paper paper) {
        paperRepository.save(paper);

    }

    @Override
    public void setStatus(Paper paper, String status) {
        Status paperStatus = statusRepository.findByStatus(status);
        paper.setStatus(paperStatus);
    }

    @Override
    public void savePaper(String title, MultipartFile paper, MultipartFile abstr, User user) {
        Paper newPaper = new Paper();
        newPaper.setTitle(title);
        List<User> users = new ArrayList<>();
        users.add(user);
        newPaper.setUsers(users);
        try {
            newPaper.setPaperFile(getByteArrayFromFile(paper));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            newPaper.setAbstracFile(getByteArrayFromFile(abstr));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setStatus(newPaper, "PENDING");

        paperRepository.save(newPaper);

    }

    @Override
    public int getNumberOfReviewers(Paper paper) {
        List<User> users = paper.getUsers();
        int number = users.size() - 1;

        return number;
    }

    @Override
    public List<User> getReviewers(Paper paper) {
        List<User> users = paper.getUsers();

        for (User user : users) {
            if (user.getRole().getRole().equals("SCIENTIST")) {
                users.remove(user);
            }
        }

        return users;
    }

    @Override
    public void addUser(Paper paper, User user) {
        List<User> users = paper.getUsers();
        users.add(user);
        paper.setUsers(users);
        paperRepository.save(paper);
    }

    @Override
    public int getNumberOfFeedbacks(Paper paper) {
        List<Feedback> feedbacks = paper.getFeedbacks();
        return feedbacks.size();
    }

    @Override
    public void setTotalScore(Paper paper) {
        int reviewersNumber = getNumberOfReviewers(paper);
        int feedbacksNumber = getNumberOfFeedbacks(paper);
        List<Feedback> feedbacks = feedbackRepository.findAllByPaper(paper);
        int score = 0;

        if (reviewersNumber == feedbacksNumber) {
            for (Feedback feedback : feedbacks) {
                score += feedback.getScore();
            }

            score = (int) (score / (double) feedbacksNumber);
            paper.setScore(score);
            savePaper(paper);
        }

    }

    @Override
    public void changeStatus(Paper paper) {
        if (paper.getScore() != null) {
            int score = paper.getScore();

            if (score >= 70) {
                setStatus(paper, "ACCEPTED");
            } else {
                setStatus(paper, "REJECTED");
            }

            savePaper(paper);
        }

    }

    private byte[] getByteArrayFromFile(final MultipartFile file) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final InputStream in = new BufferedInputStream(file.getInputStream());
        final byte[] buffer = new byte[500];
        int read = -1;

        while ((read = in.read(buffer)) > 0) {
            baos.write(buffer, 0, read);
        }

        in.close();
        return baos.toByteArray();
    }

}
