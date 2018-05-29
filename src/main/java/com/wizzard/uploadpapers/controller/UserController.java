package com.wizzard.uploadpapers.controller;

import java.io.IOException;

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

import com.wizzard.uploadpapers.entity.Paper;
import com.wizzard.uploadpapers.entity.User;
import com.wizzard.uploadpapers.service.PaperService;
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
	private PaperService paperService;
	
	@Autowired
	private FileValidator fileValidator;
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
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
		ModelAndView modelAndView;
		
		fileValidator.validate(fileUploadModel, bindingResult);
		if (bindingResult.hasErrors()) {
			modelAndView = new ModelAndView();
			modelAndView.setViewName("user/upload");
		}	
		else {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findUserByEmail(auth.getName());
			String title = fileUploadModel.getTitle();
			MultipartFile paperFile = fileUploadModel.getPaperFile();
			MultipartFile abstractFile = fileUploadModel.getAbstractFile();
			paperService.savePaper(title, paperFile, abstractFile, user);
			userService.AddPaper(user, paperService.findPaperByTitle(title));
			modelAndView = new ModelAndView("redirect:/user/files/"+title);
		}
		
		return modelAndView;
		
	}
		
	
	@RequestMapping(value = "/files", method = RequestMethod.GET)
	public ModelAndView listFiles(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("files", user.getPapers());
		modelAndView.setViewName("/user/papers");
		return modelAndView;
	}
	
	 @RequestMapping(value="/files/{filename}", method = RequestMethod.GET)
	 public ModelAndView serveFileDetails(@PathVariable String filename) {

		 	ModelAndView modelAndView = new ModelAndView();
		 	Paper paper = paperService.findPaperByTitle(filename);
		 	String title = paper.getTitle();
		 	String status = paper.getStatus().getStatus();
		 	modelAndView.addObject("title", title);
		 	modelAndView.addObject("status", status);
		 	modelAndView.setViewName("/user/files/file");
		 	return modelAndView;
	        
	    }
	 
	 @RequestMapping(value="/files/file/{type}/{filename}", method = RequestMethod.GET)
	 public HttpEntity<byte[]> downloadPdfFile(
			 @PathVariable String type, @PathVariable String filename) throws IOException {
		 
		 	Paper paper = paperService.findPaperByTitle(filename);
		 	byte [] documentBody;
		 	if (type.equalsIgnoreCase("paper"))
		 		documentBody = paper.getPaperFile();
		 	else
		 		documentBody = paper.getAbstracFile();
		 	HttpHeaders header = new HttpHeaders();
		 	header.setContentType(MediaType.APPLICATION_PDF);
		 	header.set(HttpHeaders.CONTENT_DISPOSITION,
		 			"attachment; filename="+filename.replace(" ", "_")+type.toUpperCase()+".pdf");
		 	header.setContentLength(documentBody.length);
		 	return new HttpEntity<byte[]>(documentBody, header);
	    }
	 
}
