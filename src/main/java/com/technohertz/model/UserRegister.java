package com.technohertz.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Table(name = "User_Register")
@DynamicUpdate
public class UserRegister implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userid")
	private int userId;

	@Column(name = "Source_From", length = 200)
	private String sourceFrom;

	@Column(name = "pass_word", length = 100)
	private String password;


	@Column(name = "email_id", length = 200)
	private String email;
	
	
	@Column(name = "token", nullable = true, length = 200)
	private Integer Token;
	

	@ColumnDefault("false")
	@Column(name = "status", nullable = true, length = 200)
	private Boolean isActive;
	
	@Column(name = "createDate", nullable = true)
	private LocalDateTime createDate;
	
	@Column(name = "lastModifiedDate", nullable = true, length = 200)
	private LocalDateTime lastModifiedDate;
	




	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getToken() {
		return Token;
	}

	public void setToken(Integer token) {
		Token = token;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}



}
