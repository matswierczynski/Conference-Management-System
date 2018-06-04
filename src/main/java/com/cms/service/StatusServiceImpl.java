package com.cms.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cms.entity.Status;
import com.cms.repository.StatusRepository;

public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public Status findStatusById(int id) {
        Long lId = (long) id;
        return statusRepository.getOne(lId);
    }

}
