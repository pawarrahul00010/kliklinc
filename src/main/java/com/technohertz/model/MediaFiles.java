package com.technohertz.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
@Entity
@Table(name = "Media_Files")
@DynamicUpdate
public class MediaFiles implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_Id")
	private Integer fileId;
	
	@Column(name = "File_Path")
	private String filePath;
	
	@Column(name = "File_Create_Date")
	private LocalDateTime createDate;
	
	@Column(name = "File_Last_Modified_Date")
	private LocalDateTime lastModifiedDate;
	
	@Column(name = "Total_Likes")
	private Long likes;
	
	@Column(name = "Total_Rating")
	private Long rating;
	
	@Column(name = "File_Type")
	private String fileType;
	
	@Column(name = "Bookmarked_Status")
	private Boolean isBookMarked;

	@Column(name = "Shared_Status")
	private Boolean isShared;


	@Column(name = "is_Liked")
	private Boolean isLiked;
	@Column(name = "is_Rated")
	private Boolean isRated;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name ="USR_DET_ID")
	private UserProfile profile;
	
	@ElementCollection
	@JoinTable(name="liked_users",joinColumns=@JoinColumn(name="file_Id"))
	private Collection<LikedUsers> likedUsers = new HashSet<>();
	
	
	public MediaFiles(String fileName,LocalDateTime createDate,LocalDateTime lastModifiedDate) {
		this.filePath=fileName;
		
		this.createDate=createDate;
		this.lastModifiedDate=lastModifiedDate;
	}

	public MediaFiles() {
		// TODO Auto-generated constructor stub
	}

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

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public Long getRating() {
		return rating;
	}

	public void setRating(Long rate) {
		this.rating = rate;
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


	public UserProfile getProfile() {
		return profile;
	}

	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}
	
	public Collection<LikedUsers> getLikedUsers() {
		return likedUsers;
	}

	public void setLikedUsers(Collection<LikedUsers> likedUsers) {
		this.likedUsers = likedUsers;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */


	/**
	 * @return the isLiked
	 */
	public Boolean getIsLiked() {
		return isLiked;
	}

	/**
	 * @param isLiked the isLiked to set
	 */
	public void setIsLiked(Boolean isLiked) {
		this.isLiked = isLiked;
	}

	/**
	 * @return the isRated
	 */
	public Boolean getIsRated() {
		return isRated;
	}

	/**
	 * @param isRated the isRated to set
	 */
	public void setIsRated(Boolean isRated) {
		this.isRated = isRated;
	}
	
	

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Override
	public String toString() {
		return "MediaFiles [fileId=" + fileId + ", filePath=" + filePath + ", createDate=" + createDate
				+ ", lastModifiedDate=" + lastModifiedDate + ", likes=" + likes + ", rating=" + rating
				+ ", isBookMarked=" + isBookMarked + ", isShared=" + isShared + ", profile=" + profile + ", likedUsers="
				+ likedUsers + "]";
	}






}
