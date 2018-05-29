package com.wizzard.uploadpapers.service;

import com.wizzard.uploadpapers.entity.Paper;
import com.wizzard.uploadpapers.entity.User;

public interface UserService {

	public User findUserByEmail(String email);
	public void saveUser(User user, String role);
	public void saveUser(User user);
	public void AddPaper(User user, Paper paper);
	
}
