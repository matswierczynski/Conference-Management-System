package com.wizzard.uploadpapers.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="scientist")
public class Scientist extends User {
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="paper_id", nullable=true)
	private List<Paper> papers = new ArrayList<>();

	public List<Paper> getPapers() {
		return papers;
	}

	public void setPapers(List<Paper> papers) {
		this.papers = papers;
	}
	
	
}
