package com.wizzard.uploadpapers.service;

import com.wizzard.uploadpapers.entity.Paper;

public interface PaperService {
	
	public Paper findPaperById(int id);
	public void setStatus(Paper paper, String status);
	public void savePaper(Paper paper);

}
