package com.technohertz.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "Media_Files")
@DynamicUpdate
public class MediaFiles implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fileId")
	private Integer fileId;
	
	@Column(name = "File_Path")
	private String filePath;
	
	@Column(name = "File_Create_Date", nullable = false)
	private LocalDateTime createDate;
	
	@Column(name = "File_Last_Modified_Date", nullable = false, length = 200)
	private LocalDateTime lastModifiedDate;
	
	@Column(name = "Total_Likes")
	private Integer likes;
	
	@Column(name = "Total_Rating")
	private Integer rating;
	
	@Column(name = "Bookmarked_Status", nullable = false, length = 200)
	private Boolean isBookMarked;

	@Column(name = "Shared_Status", nullable = false, length = 200)
	private Boolean isShared;
	
	@Column(name = "Liked_Status", nullable = false, length = 200)
	private Boolean isLiked;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="USR_DET_ID")
	UserProfile profile;

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Boolean getIsBookMarked() {
		return isBookMarked;
	}

	public void setIsBookMarked(Boolean isBookMarked) {
		this.isBookMarked = isBookMarked;
	}

	public Boolean getIsShared() {
		return isShared;
	}

	public void setIsShared(Boolean isShared) {
		this.isShared = isShared;
	}

	public Boolean getIsLiked() {
		return isLiked;
	}

	public void setIsLiked(Boolean isLiked) {
		this.isLiked = isLiked;
	}

	public UserProfile getProfile() {
		return profile;
	}

	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}





}
