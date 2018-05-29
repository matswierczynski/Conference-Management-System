package com.wizzard.uploadpapers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wizzard.uploadpapers.entity.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
	
	Status findByStatus(String status);
	Status findById(int id);

}
