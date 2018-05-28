package com.wizzard.uploadpapers.entity;

import javax.persistence.JoinColumn;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;

@Entity
@Table(name="user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="user_id")
	private int id;
	
	@Column(name="user_email")
	@Email(message="*please provide a vali email")
	@NotEmpty(message="*please provide an email")
	private String email;
	
	@Column(name="user_password")
	@Length(min=5, message="*Password must have at least 5 characters")
	@NotEmpty(message="*Please provide a password")
	@Transient
	private String password;
	
	@Column(name="user_name")
	@NotEmpty(message="*Please provide name")
	private String name;
	
	@Column(name="user_last_name")
	@NotEmpty(message="*Please provide last name")
	private String lastName;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="role_id", nullable=false)
	private Role role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	

}
