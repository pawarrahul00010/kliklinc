package com.technohertz.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LikedUsers implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "user_Name")
	private String userName;

	@Column(name = "type")
	private String markType;


	@Column(name = "fileID")
	private int fileID;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	/**

	 * @return the fileID
	 */
	public int getFileID() {
		return fileID;
	}
	/**
	 * @param fileID the fileID to set
	 */
	public void setFileID(int fileID) {
		this.fileID = fileID;
	}
	/**
	 * @return the markType
	 */
	public String getMarkType() {
		return markType;
	}
	/**
	 * @param markType the markType to set
	 */
	public void setMarkType(String markType) {
		this.markType = markType;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LikedUsers [userName=" + userName + ", markType=" + markType + "]";
	}
	
}
