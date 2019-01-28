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

@Entity
@Table(name = "Post_Card")
@DynamicUpdate
public class PostCard implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "POST_CARD_ID")
	private Integer postCardId;

	@Column(name = "POST_CARD_TEXT")
	private String postCardText;


	@JsonIgnore
	@OneToMany(cascade=javax.persistence.CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="POST_CARD_ID")
	private List<MediaFiles> files=new ArrayList<MediaFiles>();

	public PostCard(Integer postCardId, String postCardText, List<MediaFiles> files) {
		super();
		this.postCardId = postCardId;
		this.postCardText = postCardText;
		
		this.files = files;
	}

	public PostCard() {
		
	}

	public Integer getPostCardId() {
		return postCardId;
	}

	public void setPostCardId(Integer postCardId) {
		this.postCardId = postCardId;
	}

	public String getPostCardText() {
		return postCardText;
	}

	public void setPostCardText(String postCardText) {
		this.postCardText = postCardText;
	}


	public List<MediaFiles> getFiles() {
		return files;
	}

	public void setFiles(List<MediaFiles> files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "PostCard [postCardId=" + postCardId + ", postCardText=" + postCardText + ", files=" + files + "]";
	}

	

}
