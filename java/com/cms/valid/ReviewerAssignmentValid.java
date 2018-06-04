package com.cms.valid;

import java.util.List;

import com.cms.entity.Paper;
import com.cms.entity.User;
import com.cms.service.PaperService;
import com.cms.service.PaperServiceImpl;
import com.cms.service.UserService;
import com.cms.service.UserServiceImpl;

public class ReviewerAssignmentValid {
    public static final int MAX_REVIEWERS_NUMBER = 5;
    public static final int MAX_PAPERS_NUMBER = 5;

    private String communicate;

    private PaperService paperService = new PaperServiceImpl();
    private UserService userService = new UserServiceImpl();

    public boolean isAssignedOk(Paper paper, User reviewer) {

        if (paperService.getNumberOfReviewers(paper) >= MAX_REVIEWERS_NUMBER) {
            communicate = "Maximum number of reviewers is already assigned";
        } else if (userService.getNumberOfPapers(reviewer) >= MAX_PAPERS_NUMBER) {
            communicate = "This reviewer cannot review more papers";
        } else if (isAlreadyAssigned(paper, reviewer)) {
            communicate = "This paper is already assigned to this reviewer";
        } else {
            String title = paper.getTitle();
            String email = reviewer.getEmail();
            communicate = title + " has been assigned to " + email;
            return true;
        }

        return false;
    }

    private boolean isAlreadyAssigned(Paper paper, User reviewer) {
        List<Paper> papers = userService.getPapers(reviewer);

        for (Paper currentPaper : papers) {
            if (paper.getId() == currentPaper.getId()) {
                return true;
            }
        }

        return false;
    }

    public String getCommunicate() {
        return communicate;
    }
}
