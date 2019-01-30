package com.technohertz.model;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "User_Register")
@DynamicUpdate
public class UserRegister {

	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userid")
	private int userId;

	@Column(name = "user_name", nullable = false, length = 100)
	private String userName;

	@Column(name = "Source_From", nullable = false, length = 200)
	private String sourceFrom;

	@Column(name = "pass_word", nullable = false, length = 100)
	private String password;


	@Column(name = "mobil_number", nullable = false, length = 200)
	private Long mobilNumber;
	
	@Column(name = "token", nullable = false, length = 200)
	private long Token;


	@Column(name = "status", nullable = false, length = 200)
	private Boolean isActive;
	
	@Column(name = "createDate", nullable = false)
	private LocalDateTime createDate;
	
	@Column(name = "lastModifiedDate", nullable = false, length = 200)
	private LocalDateTime lastModifiedDate;
	

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

	@OneToOne(cascade=javax.persistence.CascadeType.ALL)
	@JoinColumn(name="USR_DET_ID")
	private UserProfile profile = new UserProfile();

	 @JsonIgnore
	@OneToOne(cascade=javax.persistence.CascadeType.ALL)
	@JoinColumn(name="POST_CARD_ID")
	private PostCard postCard = new PostCard();


	 
	 @JsonIgnore
	@OneToOne(cascade=javax.persistence.CascadeType.ALL)
	@JoinColumn(name="GREATING_CARD_ID")
	private GreatingCard greatingCard = new GreatingCard();

	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

	@OneToOne(cascade=javax.persistence.CascadeType.ALL,fetch=FetchType.LAZY)		
	@JoinColumn(name="OTP_ID")
	private UserOtp userOtp = new UserOtp();
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(cascade=javax.persistence.CascadeType.ALL,fetch=FetchType.LAZY)		
	@JoinColumn(name="userid")
	private List<UserContact> userContactList=new ArrayList<UserContact>();

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(cascade=javax.persistence.CascadeType.ALL,fetch=FetchType.LAZY)		
	@JoinColumn(name="BIOMETRIC_ID")
	
	private List<Biometric> biometric=new ArrayList<Biometric>();
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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


	public long getToken() {
		return Token;
	}

	public void setToken(long token) {
		Token = token;
	}

	public Long getMobilNumber() {
		return mobilNumber;
	}

	public void setMobilNumber(Long mobilNumber) {
		this.mobilNumber = mobilNumber;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	

	public UserProfile getProfile() {
		return profile;
	}

	public void setProfile(UserProfile profile) {
		this.profile = profile;
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
	 * @return the userOtp
	 */
	public UserOtp getUserOtp() {
		return userOtp;
	}

	/**
	 * @param userOtp the userOtp to set
	 */
	public void setUserOtp(UserOtp userOtp) {
		this.userOtp = userOtp;
	}

	
	
	/**
	 * @return the userContactList
	 */
	public List<UserContact> getUserContactList() {
		return userContactList;
	}

	/**
	 * @param userContactList the userContactList to set
	 */
	public void setUserContactList(List<UserContact> userContactList) {
		this.userContactList = userContactList;
	}

	public PostCard getPostCard() {
		return postCard;
	}

	public void setPostCard(PostCard postCard) {
		this.postCard = postCard;
	}

	public GreatingCard getGreatingCard() {
		return greatingCard;
	}

	public void setGreatingCard(GreatingCard greatingCard) {
		this.greatingCard = greatingCard;
	}

	public List<Biometric> getBiometric() {
		return biometric;
	}

	public void setBiometric(List<Biometric> biometric) {
		this.biometric = biometric;
	}

	
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */

	@Override
	public String toString() {
		return "UserRegister [userId=" + userId + ", userName=" + userName + ", sourceFrom=" + sourceFrom
				+ ", password=" + password + ", mobilNumber=" + mobilNumber + ", Token=" + Token + ", isActive="
				+ isActive + ", createDate=" + createDate + ", lastModifiedDate=" + lastModifiedDate + ", profile="
				+ profile + ", postCard=" + postCard + ", greatingCard=" + greatingCard + ", userOtp=" + userOtp
				+ ", userContactList=" + userContactList + ", biometric=" + biometric + "]";
	}
}
