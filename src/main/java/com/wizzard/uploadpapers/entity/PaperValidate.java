package com.wizzard.uploadpapers.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class PaperValidate {
	
	@Length(min = 5, max = 100, message = "*Title must have at least 5 characters and at most 100 characters")
	@NotEmpty(message="*Please provide a title")
	private String title;

}
