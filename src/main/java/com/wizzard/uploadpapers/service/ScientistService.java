package com.wizzard.uploadpapers.service;

import com.wizzard.uploadpapers.entity.Scientist;

public interface ScientistService {
	
	public Scientist findScientistByEmail(String email);
	public void saveScientist(Scientist scientist);

}
