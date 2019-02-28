package com.technohertz.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "Admin_Profile")
@DynamicUpdate
public class AdminProfile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USR_DET_ID")
	private Integer profileId;

	@Column(name = "Display_Name")
	private String displayName;

	@Column(name = "current_Profile")
	private String currentProfile;


	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}


	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	/**
	 * @return the currentProfile
	 */
	public String getCurrentProfile() {
		return currentProfile;
	}

	/**
	 * @param currentProfile the currentProfile to set
	 */
	public void setCurrentProfile(String currentProfile) {
		this.currentProfile = currentProfile;
	}



	
}
