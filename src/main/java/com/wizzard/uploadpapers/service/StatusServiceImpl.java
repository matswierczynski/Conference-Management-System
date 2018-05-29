package com.wizzard.uploadpapers.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.wizzard.uploadpapers.entity.Status;
import com.wizzard.uploadpapers.repository.StatusRepository;

public class StatusServiceImpl implements StatusService {
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Override
	public Status findStatusById(int id) {
		Long lId = (long)id;
		return statusRepository.getOne(lId);
	}

}
