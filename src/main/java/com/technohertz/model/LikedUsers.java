package com.technohertz.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class LikedUsers implements Serializable {

	@Column(name = "user_Name")
	private String userName;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "LikedUsers [userName=" + userName + "]";
	}

	
}
