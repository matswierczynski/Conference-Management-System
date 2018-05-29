package com.wizzard.uploadpapers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wizzard.uploadpapers.entity.Paper;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long>{
	Paper findById(int id);
	Paper findByTitle(String title);

}
