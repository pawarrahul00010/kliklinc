package com.technohertz.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "USER_OTP")
public class UserOtp {

	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OTPID")
	private int otpId;
	
	@Column(name = "USERID")
	private int userId;

	@Column(name = "OTP")
	private int otp;
	
	@Column(name = "CREATE_DATE", nullable = false, length = 200)
	private LocalDateTime createDate;
	
	@Column(name = "LAST_MODIFIED_DATE", nullable = false, length = 200)
	private LocalDateTime lastModifiedDate;

	public UserOtp() {
		super();
	}

	/**
	 * @param otpId
	 * @param userId
	 * @param otp
	 * @param createDate
	 * @param lastModifiedDate
	 */
	public UserOtp(int otpId, int userId, int otp, LocalDateTime createDate, LocalDateTime lastModifiedDate) {
		super();
		this.otpId = otpId;
		this.userId = userId;
		this.otp = otp;
		this.createDate = createDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * @return the otpId
	 */
	public int getOtpId() {
		return otpId;
	}

	/**
	 * @param otpId the otpId to set
	 */
	public void setOtpId(int otpId) {
		this.otpId = otpId;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the otp
	 */
	public int getOtp() {
		return otp;
	}

	/**
	 * @param otp the otp to set
	 */
	public void setOtp(int otp) {
		this.otp = otp;
	}

	/**
	 * @return the createDate
	 */
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the lastModifiedDate
	 */
	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserOtp [otpId=" + otpId + ", userId=" + userId + ", otp=" + otp + ", createDate=" + createDate
				+ ", lastModifiedDate=" + lastModifiedDate + "]";
	}


}
