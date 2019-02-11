package com.technohertz.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

@Transactional
@Entity
@Table(name = "USER_CONTACT")
public class UserContact implements Comparable<UserContact>, Serializable{

	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CONTACT_ID")
	private int contactId;
	
	@Column(name = "CONTACT_NAME")
	private String contactName;

	@Column(name = "CONTACT_NUMBER")
	private String contactNumber;
	
	@Column(name = "profile_Pic")
	private String profilePic;

	@Column(name = "ISBLOCKED")
	private boolean isBlocked;

	@Column(name = "ISACTIVE")
	private boolean isActive;
	
	@Column(name = "CREATE_DATE", nullable = false, length = 200)
	private LocalDateTime createDate;
	
	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<GroupProfile> groupList = new ArrayList<>();
	
	public UserContact() {
		super();
	}


	/**
	 * @param contactId
	 * @param contactName
	 * @param contactNumber
	 * @param profilePic
	 * @param isBlocked
	 * @param isActive
	 * @param createDate
	 * @param groupList
	 */
	public UserContact(int contactId, String contactName, String contactNumber, String profilePic, boolean isBlocked,
			boolean isActive, LocalDateTime createDate) {
		super();
		this.contactId = contactId;
		this.contactName = contactName;
		this.contactNumber = contactNumber;
		this.profilePic = profilePic;
		this.isBlocked = isBlocked;
		this.isActive = isActive;
		this.createDate = createDate;
	}


	/**
	 * @return the contactId
	 */
	public int getContactId() {
		return contactId;
	}

	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return the contactNumber
	 */
	public String isContactNumber() {
		return contactNumber;
	}

	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * @return the isBlocked
	 */
	public boolean isBlocked() {
		return isBlocked;
	}

	/**
	 * @param isBlocked the isBlocked to set
	 */
	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
	 * @return the profilePic
	 */
	public String getProfilePic() {
		return profilePic;
	}


	/**
	 * @param profilePic the profilePic to set
	 */
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}


	/**
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserContact [contactId=" + contactId + ", contactName=" + contactName + ", contactNumber="
				+ contactNumber + ", profilePic=" + profilePic + ", isBlocked=" + isBlocked + ", isActive=" + isActive
				+ ", createDate=" + createDate + ", groupList=" + groupList + "]";
	}


	public int compareTo(UserContact o) {

		return (int) (o.getContactId()-this.getContactId());
	}



}
