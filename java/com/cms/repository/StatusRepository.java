package com.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cms.entity.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

    Status findByStatus(String status);

    Status findById(int id);

}
