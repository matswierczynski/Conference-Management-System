package com.wizzard.uploadpapers.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wizzard.uploadpapers.entity.User;
import com.wizzard.uploadpapers.service.UserService;
import com.wizzard.uploadpapers.storage.FileUploadModel;
import com.wizzard.uploadpapers.storage.FileValidator;;

@Controller
@ComponentScan(basePackages = {"com.wizzard.uploadpapers.service", "com.wizzard.uploadpapers.storage"})
@RequestMapping(value="/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileValidator fileValidator;
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Participant Role");
		modelAndView.setViewName("user/home");
		return modelAndView;
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView upload() {
		ModelAndView modelAndView = new ModelAndView();
		FileUploadModel fileUploadModel = new FileUploadModel();
		modelAndView.addObject("fileUploadModel", fileUploadModel);
		modelAndView.setViewName("user/upload");
		return modelAndView;
		
	}
	@RequestMapping(value = "/upload", method=RequestMethod.POST)
    public ModelAndView handleFileUpload(Model model, @ModelAttribute FileUploadModel fileUploadModel, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		
		fileValidator.validate(fileUploadModel, bindingResult);
		if (bindingResult.hasErrors())
			modelAndView.setViewName("user/upload");
		else {
			String title = fileUploadModel.getTitle();
			MultipartFile paperFile = fileUploadModel.getPaperFile();
			MultipartFile abstratcFile = fileUploadModel.getAbstractFile();
		}
		
		return modelAndView;
		
       /* storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";*/
    }
	

}
