package com.technohertz.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "User_Profile")
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USR_DET_ID")
	private Integer profileId;
	@Column(name = "Profile_Pic")
	private String prifilePic;
	@Column(name = "Display_Name")
	private String displayName;
	@Column(name = "About_User")
	private String aboutUser;



	@OneToOne
	@PrimaryKeyJoinColumn
	private UserRegister register;
	
	public UserRegister getRegister() {
		return register;
	}

	public void setRegister(UserRegister register) {
		this.register = register;
	}



	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	public String getPrifilePic() {
		return prifilePic;
	}

	public void setPrifilePic(String prifilePic) {
		this.prifilePic = prifilePic;
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


	@Override
	public String toString() {
		return "UserProfile [profileId=" + profileId + ", prifilePic=" + prifilePic + ", displayName=" + displayName
				+ ", aboutUser=" + aboutUser + "]";
	}

}
