package com.technohertz.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class VoUserRegister implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;

	private String sourceFrom;

	@NotNull
	@Min(6)
	private String password;

	@NotNull
	private String email;
	
	private Integer Token;
	

	private Boolean isActive;

	@NotNull
	private LocalDateTime createDate;

	@NotNull
	private LocalDateTime lastModifiedDate;
	


	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
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
