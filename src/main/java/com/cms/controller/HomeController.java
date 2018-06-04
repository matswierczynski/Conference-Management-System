package com.cms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cms.entity.User;
import com.cms.service.UserService;

//handles all request for login, registration, news, schedule
@Controller
@ComponentScan(basePackages = { "com.cms.service" })
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        if (user == null) {
            modelAndView.setViewName("home");
            return modelAndView;
        }

        String role = user.getRole().getRole();

        if (role.equals("PARTICIPANT")) {
            modelAndView.setViewName("home-participant");
        } else if (role.equals("REVIEWER")) {
            modelAndView.setViewName("home-reviewer");
        } else if (role.equals("SCIENTIST")) {
            modelAndView.setViewName("home-scientist");
        } else if (role.equals("ORGANIZER")) {
            modelAndView.setViewName("home-organizer");
        } else {
            modelAndView.setViewName("home");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/home-participant", method = RequestMethod.GET)
    public ModelAndView homeParticipant() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home-participant");
        return modelAndView;
    }

    @RequestMapping(value = "/home-reviewer", method = RequestMethod.GET)
    public ModelAndView homeReviewer() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home-reviewer");
        return modelAndView;
    }

    @RequestMapping(value = "/home-scientist", method = RequestMethod.GET)
    public ModelAndView homeScientist() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home-scientist");
        return modelAndView;
    }

    @RequestMapping(value = "/home-organizer", method = RequestMethod.GET)
    public ModelAndView homeOrganizer() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home-organizer");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult,
            @RequestParam(value = "scientistReg", required = false) String checkboxValue) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists;

        userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult.rejectValue("email", "error.user",
                    "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            if (checkboxValue != null)
                userService.saveUser(user, "SCIENTIST");
            else
                userService.saveUser(user, "PARTICIPANT");

            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("scientist", new User());
            modelAndView.setViewName("registration");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public ModelAndView showSchedule() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("schedule");
        return modelAndView;
    }

}