package com.wizzard.uploadpapers.service;

import org.springframework.web.multipart.MultipartFile;

import com.wizzard.uploadpapers.entity.Paper;
import com.wizzard.uploadpapers.entity.User;

public interface PaperService {
	
	public Paper findPaperById(int id);
	public Paper findPaperByTitle(String title);
	public void setStatus(Paper paper, String status);
	public void savePaper(Paper paper);
	public void savePaper(String title, MultipartFile paper, MultipartFile abstr, User user);

}
