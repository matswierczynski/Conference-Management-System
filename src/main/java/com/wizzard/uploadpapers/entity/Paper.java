package com.wizzard.uploadpapers.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="paper")
public class Paper {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "paper_id")
	private int id;
	
	@Column(name = "paper_title")
	private String title;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="status_id", nullable=false)
	private Status status;
	
	@Column(name = "paper_file")
	@Lob
	private byte[] paperFile;
	
	@Column(name = "abstract_file")
	@Lob
	private byte[] abstracFile;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public byte[] getPaperFile() {
		return paperFile;
	}

	public void setPaperFile(byte[] paperFile) {
		this.paperFile = paperFile;
	}

	public byte[] getAbstracFile() {
		return abstracFile;
	}

	public void setAbstracFile(byte[] abstracFile) {
		this.abstracFile = abstracFile;
	}
	

	
}
