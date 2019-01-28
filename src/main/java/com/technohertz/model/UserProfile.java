package com.technohertz.model;

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

@Entity
@Table(name = "User_Profile")
@DynamicUpdate
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USR_DET_ID")
	private Integer profileId;

	@Column(name = "Display_Name")
	private String displayName;
	@Column(name = "About_User")
	private String aboutUser;

	@OneToMany(cascade=javax.persistence.CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="USR_DET_ID")
	private List<MediaFiles> files=new ArrayList<MediaFiles>();

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
		return aboutUser;
	}

	public void setAboutUser(String aboutUser) {
		this.aboutUser = aboutUser;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserProfile [profileId=" + profileId + ", displayName=" + displayName + ", aboutUser=" + aboutUser
				+ ", files=" + files + "]";
	}



}
