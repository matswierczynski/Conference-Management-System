package com.wizzard.uploadpapers.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wizzard.uploadpapers.entity.Paper;
import com.wizzard.uploadpapers.entity.Status;
import com.wizzard.uploadpapers.entity.User;
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
	public Paper findPaperByTitle(String title) {
		return paperRepository.findByTitle(title);
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
	
	@Override
	public void savePaper(String title, MultipartFile paper, MultipartFile abstr, User user) {
		Paper newPaper = new Paper();
		newPaper.setTitle(title);
		newPaper.setUser(user);
		try {
			newPaper.setPaperFile(
					getByteArrayFromFile(paper));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			newPaper.setAbstracFile(
					getByteArrayFromFile(abstr));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setStatus(newPaper, "PENDING");
		
		paperRepository.save(newPaper);
		
	}
	
	private byte[] getByteArrayFromFile(final MultipartFile file) throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final InputStream in = new BufferedInputStream(file.getInputStream());
		final byte[] buffer = new byte[500];
		int read = -1;
		while ((read = in.read(buffer)) > 0) {
		    baos.write(buffer, 0, read);
		}
		in.close();
		return baos.toByteArray();
		}

}
