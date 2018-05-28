package com.wizzard.uploadpapers.service;

import com.wizzard.uploadpapers.entity.User;

public interface UserService {

	public User findUserByEmail(String email);
	public void saveUser(User user);
	
}
