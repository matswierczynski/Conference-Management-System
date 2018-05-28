package com.wizzard.uploadpapers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wizzard.uploadpapers.entity.Role;
import com.wizzard.uploadpapers.entity.Scientist;
import com.wizzard.uploadpapers.repository.RoleRepository;
import com.wizzard.uploadpapers.repository.ScientistRepository;

@Service
@ComponentScan(basePackages = {"com.wizzard.uploadpapers.repository", 
								"com.wizzard.uploadpapers.configuration"})
public class ScientistServiceImpl implements ScientistService {


	@Autowired
	private ScientistRepository scientistRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Scientist findScientistByEmail(String email) {
		return scientistRepository.findByEmail(email);
	}

	@Override
	public void saveScientist(Scientist scientist) {
		scientist.setPassword(bCryptPasswordEncoder.
				encode(scientist.getPassword()));
		Role userRole = roleRepository.findByRole("SCIENTIST");
		scientist.setRole(userRole);
		scientistRepository.save(scientist);
	}
	
}

