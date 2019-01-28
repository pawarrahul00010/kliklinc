package com.technohertz.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_OTP")

public class UserOtp implements Comparable<UserOtp>,Serializable {



	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OTP_ID")
	private int otpId;
	
	@Column(name = "OTP")
	private int otp;

	@Column(name = "ISACTIVE")
	private boolean is_active;
	
	@Column(name = "CREATE_DATE", nullable = false, length = 200)
	private LocalDateTime createDate;
	
	@Column(name = "LAST_MODIFIED_DATE", nullable = false, length = 200)
	private LocalDateTime lastModifiedDate;
	
	public UserOtp() {
		super();
	}


	/**
	 * @param otpId
	 * @param reg_Id
	 * @param otp
	 * @param createDate
	 * @param lastModifiedDate
	 */
	public UserOtp(int otpId, int reg_Id, int otp, LocalDateTime createDate, LocalDateTime lastModifiedDate) {
		super();
		this.otpId = otpId;
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
	 * @return the is_active
	 */
	public boolean isIs_active() {
		return is_active;
	}


	/**
	 * @param is_active the is_active to set
	 */
	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
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
		return "UserOtp [otpId=" + otpId + ", otp=" + otp + ", is_active=" + is_active + ", createDate=" + createDate
				+ ", lastModifiedDate=" + lastModifiedDate + "]";
	}


	@Override
	public int compareTo(UserOtp o) {

		return (int) (o.getOtpId()-this.getOtpId());
	}



}
