package com.wizzard.uploadpapers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wizzard.uploadpapers.entity.Scientist;

@Repository
public interface ScientistRepository extends JpaRepository<Scientist, Long>{

		Scientist findByEmail(String email);
		

}
