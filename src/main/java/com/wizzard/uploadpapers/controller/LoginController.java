package com.wizzard.uploadpapers.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wizzard.uploadpapers.entity.User;
import com.wizzard.uploadpapers.service.UserService;

//handles all request for login, registration, news, schedule
@Controller
@ComponentScan(basePackages = {"com.wizzard.uploadpapers.service"})
public class LoginController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
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
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} 
		else {
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
	
	@RequestMapping(value = "/news", method=RequestMethod.GET)
    public ModelAndView showNews() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("news");
		return modelAndView;
	}
	
	@RequestMapping(value = "/schedule", method=RequestMethod.GET)
    public ModelAndView showSchedule() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("schedule");
		return modelAndView;
	}
	
	
}