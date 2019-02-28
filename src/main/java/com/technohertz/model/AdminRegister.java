package com.technohertz.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "Admin_Register")
@DynamicUpdate
public class AdminRegister implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "adminId")
	private int adminId;

	@Column(name = "Source_From", nullable = true, length = 200)
	private String sourceFrom;

	@Column(name = "pass_word", nullable = false, length = 100)
	private String password;


	@Column(name = "mail_id", nullable = false, length = 200)
	private String mailId;
	
	@Column(name = "token", nullable = true, length = 200)
	private long Token;


	@Column(name = "status", nullable = true, length = 200)
	private Boolean isActive;
	
	@Column(name = "createDate", nullable = true)
	private LocalDateTime createDate;
	
	@Column(name = "lastModifiedDate", nullable = true, length = 200)
	private LocalDateTime lastModifiedDate;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(cascade=javax.persistence.CascadeType.ALL,fetch=FetchType.LAZY)		
	@JoinColumn(name="userid")
	private List<UserRegister> userList=new ArrayList<UserRegister>();


	
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


	public long getToken() {
		return Token;
	}

	public void setToken(long token) {
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


	/**
	 * @return the adminId
	 */
	public int getAdminId() {
		return adminId;
	}

	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}


	/**
	 * @return the mailId
	 */
	public String getMailId() {
		return mailId;
	}

	/**
	 * @param mailId the mailId to set
	 */
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	
	/**
	 * @return the userList
	 */
	public List<UserRegister> getUserList() {
		return userList;
	}

	/**
	 * @param userList the userList to set
	 */
	public void setUserList(List<UserRegister> userList) {
		this.userList = userList;
	}

	@Override
	public String toString() {
		return "AdminRegister [adminId=" + adminId + ", sourceFrom=" + sourceFrom + ", password=" + password
				+ ", mailId=" + mailId + ", Token=" + Token + ", isActive=" + isActive + ", createDate=" + createDate
				+ ", lastModifiedDate=" + lastModifiedDate + ", userList=" + userList + "]";
	}





}
