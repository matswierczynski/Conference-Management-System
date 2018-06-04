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

import com.cms.entity.Feedback;
import com.cms.entity.Paper;
import com.cms.entity.User;
import com.cms.model.FileUploadModel;
import com.cms.model.ReviewerDetails;
import com.cms.service.FeedbackService;
import com.cms.service.PaperService;
import com.cms.service.UserService;
import com.cms.valid.FileValidator;;

@Controller
@ComponentScan(basePackages = { "com.cms.service", "com.cms.valid" })
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaperService paperService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FileValidator fileValidator;

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView upload() {
        ModelAndView modelAndView = new ModelAndView();
        FileUploadModel fileUploadModel = new FileUploadModel();
        modelAndView.addObject("fileUploadModel", fileUploadModel);
        modelAndView.setViewName("user/upload");
        return modelAndView;

    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView handleFileUpload(Model model, @ModelAttribute FileUploadModel fileUploadModel,
            BindingResult bindingResult) {
        ModelAndView modelAndView;

        fileValidator.validate(fileUploadModel, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView = new ModelAndView();
            modelAndView.setViewName("user/upload");
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            String title = fileUploadModel.getTitle();
            MultipartFile paperFile = fileUploadModel.getPaperFile();
            MultipartFile abstractFile = fileUploadModel.getAbstractFile();
            paperService.savePaper(title, paperFile, abstractFile, user);
            userService.AddPaper(user, paperService.findPaperByTitle(title));
            modelAndView = new ModelAndView("redirect:/user/files/" + title);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public ModelAndView listFiles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("files", user.getPapers());
        modelAndView.setViewName("/user/papers");
        return modelAndView;
    }

    @RequestMapping(value = "/files/{filename}", method = RequestMethod.GET)
    public ModelAndView serveFileDetails(@PathVariable String filename) {
        ModelAndView modelAndView = new ModelAndView();
        Paper paper = paperService.findPaperByTitle(filename);
        String title = paper.getTitle();
        String status = paper.getStatus().getStatus();
        Integer score = paper.getScore();
        Integer reviewersNumber = paperService.getNumberOfReviewers(paper);
        List<Feedback> feedbacks = paper.getFeedbacks();
        List<ReviewerDetails> reviewers = new ArrayList<>();

        for (Feedback feedback : feedbacks) {
            ReviewerDetails details = new ReviewerDetails();
            User reviewer = feedback.getUser();
            String firstName = reviewer.getName();
            String lastName = reviewer.getLastName();
            String email = reviewer.getEmail();
            details.setFirstName(firstName);
            details.setLastName(lastName);
            details.setEmail(email);
            reviewers.add(details);
        }

        modelAndView.addObject("title", title);
        modelAndView.addObject("status", status);
        modelAndView.addObject("score", score);
        modelAndView.addObject("reviewersNumber", reviewersNumber);
        modelAndView.addObject("reviewers", reviewers);
        modelAndView.setViewName("/user/files/file");
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

    @RequestMapping(value = "/files/file/feedback/{title}/{reviewer}", method = RequestMethod.GET)
    public HttpEntity<byte[]> downloadFeedbackFile(@PathVariable String title, @PathVariable String reviewer) {
        User rev = userService.findUserByEmail(reviewer);
        Paper paper = paperService.findPaperByTitle(title);
        Feedback feedback = feedbackService.findByPaperAndReviewer(rev, paper);
        byte[] documentBody = feedback.getFeedbackFile();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + title.replace(" ", "_") + "FEEDBACK" + ".pdf");
        header.setContentLength(documentBody.length);
        return new HttpEntity<byte[]>(documentBody, header);
    }

}
