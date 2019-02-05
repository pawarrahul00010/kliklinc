package com.technohertz.model;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.technohertz.ContactRestController;

@Entity
@Table(name = "Group_Profile")
@DynamicUpdate
public class GroupProfile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GROUP_PROFILE_ID")
	private Integer profileId;

	@Column(name = "Display_Name")
	private String displayName;

	@Column(name = "current_Profile")
	private String currentProfile;

	@Column(name = "About_User")
	private String aboutGroup;

	@JsonIgnore
	@OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_PROFILE_ID")
	private List<MediaFiles> files = new ArrayList<MediaFiles>();

	@JsonIgnore
	@OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_PROFILE_ID")
	private List<UserContact> groupMember = new ArrayList<UserContact>();

	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	public List<MediaFiles> getFiles() {
		return files;
	}

	public void setFiles(List<MediaFiles> files) {
		this.files = files;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getAboutUser() {
		return aboutGroup;
	}

	public void setAboutUser(String aboutGroup) {
		this.aboutGroup = aboutGroup;
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

	/**
	 * @return the aboutGroup
	 */
	public String getAboutGroup() {
		return aboutGroup;
	}

	/**
	 * @param aboutGroup the aboutGroup to set
	 */
	public void setAboutGroup(String aboutGroup) {
		this.aboutGroup = aboutGroup;
	}

	/**
	 * @return the groupMember
	 */
	public List<UserContact> getGroupMember() {
		return groupMember;
	}

	/**
	 * @param groupMember the groupMember to set
	 */
	public void setGroupMember(List<UserContact> groupMember) {
		this.groupMember = groupMember;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GroupProfile [profileId=" + profileId + ", displayName=" + displayName + ", currentProfile="
				+ currentProfile + ", aboutGroup=" + aboutGroup + ", files=" + files + ", groupMember=" + groupMember
				+ "]";
	}

}
