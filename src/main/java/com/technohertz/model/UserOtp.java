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
public class UserOtp implements Comparable<UserOtp>{

	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OTPID")
	private int otpId;
	
	@Column(name = "REG_ID")
	private int reg_Id;

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
	 * @param reg_Id
	 * @param otp
	 * @param createDate
	 * @param lastModifiedDate
	 */
	public UserOtp(int otpId, int reg_Id, int otp, LocalDateTime createDate, LocalDateTime lastModifiedDate) {
		super();
		this.otpId = otpId;
		this.reg_Id = reg_Id;
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

	/**
	 * @return the reg_Id
	 */
	public int getReg_Id() {
		return reg_Id;
	}

	/**
	 * @param reg_Id the reg_Id to set
	 */
	public void setReg_Id(int reg_Id) {
		this.reg_Id = reg_Id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserOtp [otpId=" + otpId + ", reg_Id=" + reg_Id + ", otp=" + otp + ", createDate=" + createDate
				+ ", lastModifiedDate=" + lastModifiedDate + "]";
	}


	@Override
	public int compareTo(UserOtp o) {

		return (int) (o.getReg_Id()-this.getReg_Id());
	}



}
