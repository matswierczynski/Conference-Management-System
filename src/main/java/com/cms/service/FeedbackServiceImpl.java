package com.cms.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cms.entity.Feedback;
import com.cms.entity.Paper;
import com.cms.entity.User;
import com.cms.repository.FeedbackRepository;

@Service
@ComponentScan(basePackages = { "com.cms.repository" })
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public void saveFeedback(User user, Paper paper, int score, MultipartFile feedbackFile) {
        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setPaper(paper);
        feedback.setScore(score);

        try {
            feedback.setFeedbackFile(getByteArrayFromFile(feedbackFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        feedbackRepository.save(feedback);
    }

    @Override
    public boolean isAlreadyReviewed(User user, Paper paper) {
        List<Feedback> feedbacks = feedbackRepository.findAllByPaper(paper);
        int userId = user.getId();

        for (Feedback feedback : feedbacks) {
            int feedbackUserId = feedback.getUser().getId();

            if (userId == feedbackUserId) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Feedback findByPaperAndReviewer(User user, Paper paper) {
        Feedback feed = null;
        List<Feedback> feedbacks = feedbackRepository.findAllByPaper(paper);
        int userId = user.getId();

        for (Feedback feedback : feedbacks) {
            int feedbackUserId = feedback.getUser().getId();

            if (userId == feedbackUserId) {
                feed = feedback;
            }
        }

        return feed;
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
