package com.cms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cms.entity.Paper;
import com.cms.entity.User;
import com.cms.model.FeedbackDetails;
import com.cms.model.FeedbackUploadModel;
import com.cms.service.FeedbackService;
import com.cms.service.PaperService;
import com.cms.service.UserService;
import com.cms.valid.FeedbackValidator;

@Controller
@ComponentScan(basePackages = { "com.cms.service", "com.cms.valid" })
@RequestMapping(value = "/review")
public class ReviewController {

    @Autowired
    private PaperService paperService;

    @Autowired
    private UserService userService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FeedbackValidator feedBackValidator;

    private String filenm;

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public ModelAndView listFiles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<FeedbackDetails> feedbackDetails = new ArrayList<>();
        List<Paper> papers = user.getPapers();

        for (Paper paper : papers) {
            paperService.setTotalScore(paper);
            paperService.changeStatus(paper);

            FeedbackDetails details = new FeedbackDetails();
            details.setTitle(paper.getTitle());
            details.setStatus(paper.getStatus().getStatus());
            details.setAlreadyReviewed(feedbackService.isAlreadyReviewed(user, paper));
            feedbackDetails.add(details);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("files", feedbackDetails);
        modelAndView.setViewName("/review/papers");
        return modelAndView;
    }

    @RequestMapping(value = "/upload/{filename}", method = RequestMethod.GET)
    public ModelAndView upload(@PathVariable String filename) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Paper paper = paperService.findPaperByTitle(filename);
        String title = paper.getTitle();
        ModelAndView modelAndView = new ModelAndView();

        if (feedbackService.isAlreadyReviewed(user, paper)) {
            modelAndView.setViewName("review/already-reviewed");
        } else {
            FeedbackUploadModel feedbackUploadModel = new FeedbackUploadModel();
            filenm = filename;
            modelAndView.addObject("feedbackUploadModel", feedbackUploadModel);
            modelAndView.addObject("title", title);
            modelAndView.setViewName("review/upload");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/files/file/{type}/{filename}", method = RequestMethod.GET)
    public HttpEntity<byte[]> downloadPdfFile(@PathVariable String type, @PathVariable String filename)
            throws IOException {
        Paper paper = paperService.findPaperByTitle(filename);
        byte[] documentBody;
        if (type.equalsIgnoreCase("paper"))
            documentBody = paper.getPaperFile();
        else
            documentBody = paper.getAbstracFile();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + filename.replace(" ", "_") + type.toUpperCase() + ".pdf");
        header.setContentLength(documentBody.length);
        return new HttpEntity<byte[]>(documentBody, header);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView handleFileUpload(Model model, @ModelAttribute FeedbackUploadModel feedbackUploadModel,
            BindingResult bindingResult) {
        ModelAndView modelAndView;

        feedBackValidator.validate(feedbackUploadModel, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView = new ModelAndView();
            modelAndView.setViewName("/review/upload");
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            Paper paper = paperService.findPaperByTitle(filenm);
            int score = feedbackUploadModel.getScore();
            MultipartFile feedbackFile = feedbackUploadModel.getFeedbackFile();
            feedbackService.saveFeedback(user, paper, score, feedbackFile);
            modelAndView = new ModelAndView("redirect:/review/files/");
        }

        return modelAndView;

    }

}
