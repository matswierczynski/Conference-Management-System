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

import com.wizzard.uploadpapers.entity.Scientist;
import com.wizzard.uploadpapers.entity.User;
import com.wizzard.uploadpapers.service.ScientistService;
import com.wizzard.uploadpapers.service.UserService;

@Controller
@ComponentScan(basePackages = {"com.wizzard.uploadpapers.service"})
public class LoginController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ScientistService scientistService;

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		Scientist scientist = new Scientist();
		modelAndView.addObject("scientist", scientist);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid Scientist scientist, BindingResult bindingResult, 
			@RequestParam(value = "scientistReg", required = false) String checkboxValue) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists;
		Scientist scientistExists;
		
		scientistExists = scientistService.findScientistByEmail(scientist.getEmail());
		userExists = userService.findUserByEmail(scientist.getEmail());
		if (userExists != null || scientistExists != null) {
			bindingResult
					.rejectValue("email", "error.scientist",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} 
		else {
			if (checkboxValue != null)
				scientistService.saveScientist(scientist);
			else
				userService.saveUser(scientist);
			
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("scientist", new Scientist());
			modelAndView.setViewName("registration");
			}
		
		return modelAndView;
	}
	
	
}