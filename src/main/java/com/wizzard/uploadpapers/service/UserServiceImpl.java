package com.wizzard.uploadpapers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wizzard.uploadpapers.entity.Paper;
import com.wizzard.uploadpapers.entity.Role;
import com.wizzard.uploadpapers.entity.User;
import com.wizzard.uploadpapers.repository.PaperRepository;
import com.wizzard.uploadpapers.repository.RoleRepository;
import com.wizzard.uploadpapers.repository.UserRepository;

@Service
@ComponentScan(basePackages = {"com.wizzard.uploadpapers.repository", 
								"com.wizzard.uploadpapers.configuration"})
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	@Override
	public void saveUser(User user, String role) {
		user.setPassword(bCryptPasswordEncoder.
				encode(user.getPassword()));
		Role userRole = roleRepository.findByRole(role);
		user.setRole(userRole);
		userRepository.save(user);
		
	}
	
	@Override
	public void saveUser(User user) {
		userRepository.save(user);
	}
	

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public void AddPaper(User user, Paper paper) {
		List<Paper> listOfPapers = user.getPapers();
		listOfPapers.add(paper);
		user.setPapers(listOfPapers);	
	}

}
