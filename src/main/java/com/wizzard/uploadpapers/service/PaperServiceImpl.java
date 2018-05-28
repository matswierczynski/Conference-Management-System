package com.wizzard.uploadpapers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import com.wizzard.uploadpapers.entity.Paper;
import com.wizzard.uploadpapers.entity.Status;
import com.wizzard.uploadpapers.repository.PaperRepository;
import com.wizzard.uploadpapers.repository.StatusRepository;

@Service
@ComponentScan(basePackages = {"com.wizzard.uploadpapers.repository", 
								"com.wizzard.uploadpapers.configuration"})
public class PaperServiceImpl implements PaperService{
	
	@Autowired
	private PaperRepository paperRepository;
	
	@Autowired
	private StatusRepository statusRepository;

	@Override
	public Paper findPaperById(int id) {
		Long lId = (long)id;
		return paperRepository.getOne(lId);
	}

	@Override
	public void savePaper(Paper paper) {
		paperRepository.save(paper);
		
	}
	
	@Override
	public void setStatus(Paper paper, String status) {
		Status paperStatus = statusRepository.findByStatus(status);
		paper.setStatus(paperStatus);
	}	

}
