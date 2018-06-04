package com.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cms.entity.Paper;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long> {
    Paper findById(int id);

    Paper findByTitle(String title);

    List<Paper> findAllByOrderByTitleAsc();

}
