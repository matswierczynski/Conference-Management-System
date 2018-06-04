package com.cms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cms.entity.Paper;
import com.cms.entity.User;
import com.cms.model.PaperDetails;
import com.cms.model.ReviewerDetails;
import com.cms.service.PaperService;
import com.cms.service.UserService;
import com.cms.valid.ReviewerAssignmentValid;

@Controller
@ComponentScan(basePackages = { "com.cms.service" })
@RequestMapping(value = "/assign")
public class AssignController {

    @Autowired
    private PaperService paperService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public ModelAndView listFiles() {
        List<PaperDetails> paperDetails = new ArrayList<>();
        List<Paper> papers = paperService.findAllByOrderByTitleAsc();

        for (Paper paper : papers) {
            PaperDetails details = new PaperDetails();
            details.setPaperTitle(paper.getTitle());
            details.setScore(paper.getScore());
            details.setReviewersNumber(paperService.getNumberOfReviewers(paper));
            details.setStatus(paper.getStatus().getStatus());
            paperDetails.add(details);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("files", paperDetails);
        modelAndView.setViewName("/assign/papers");
        return modelAndView;
    }

    @RequestMapping(value = "/files/{filename}", method = RequestMethod.GET)
    public ModelAndView serveReviewerAssignmentView(@PathVariable String filename) {
        ModelAndView modelAndView = new ModelAndView();
        Paper paper = paperService.findPaperByTitle(filename);
        Integer reviewersNumber = paperService.getNumberOfReviewers(paper);
        String title = paper.getTitle();
        modelAndView.addObject("title", title);

        List<ReviewerDetails> reviewerDetails = new ArrayList<>();
        List<User> reviewers = userService.findAllByRole("REVIEWER");

        for (User reviewer : reviewers) {
            ReviewerDetails details = new ReviewerDetails();
            details.setFirstName(reviewer.getName());
            details.setLastName(reviewer.getLastName());
            details.setEmail(reviewer.getEmail());
            details.setPapersNumber(userService.getNumberOfPapers(reviewer));
            details.setConflictFactor(0);
            reviewerDetails.add(details);
        }

        modelAndView.addObject("reviewers", reviewerDetails);
        modelAndView.addObject("reviewersNumber", reviewersNumber);
        modelAndView.addObject("title", title);
        modelAndView.setViewName("/assign/paper-reviewer");
        return modelAndView;
    }

    @RequestMapping(value = "/assign-reviewer/{title}/{email}", method = RequestMethod.GET)
    public ModelAndView confirmAssignment(@PathVariable String title, @PathVariable String email) {
        Paper paper = paperService.findPaperByTitle(title);
        User reviewer = userService.findUserByEmail(email);
        ReviewerAssignmentValid valid = new ReviewerAssignmentValid();

        if (valid.isAssignedOk(paper, reviewer)) {
            paperService.addUser(paper, reviewer);
        }

        String communicate = valid.getCommunicate();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("communicate", communicate);
        modelAndView.setViewName("/assign/assign-reviewer");
        return modelAndView;
    }

}
