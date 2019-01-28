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
@Table(name = "GREATING_CARD")
@DynamicUpdate
public class GreatingCard implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GREATING_CARD_ID")
	private Integer greatingCardId;

	@Column(name = "GREATING_CARD_TEXT")
	private String greatingCardText;


	@JsonIgnore
	@OneToMany(cascade=javax.persistence.CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="GREATING_CARD_ID")
	private List<MediaFiles> files=new ArrayList<MediaFiles>();

	public GreatingCard(Integer postCardId, String postCardText, List<MediaFiles> files) {
		super();
		this.greatingCardId = postCardId;
		this.greatingCardText = postCardText;

		this.files = files;
	}

	public GreatingCard() {
		
	}

	public Integer getPostCardId() {
		return greatingCardId;
	}

	public void setPostCardId(Integer postCardId) {
		this.greatingCardId = postCardId;
	}

	public String getPostCardText() {
		return greatingCardText;
	}

	public void setPostCardText(String greatingCardText) {
		this.greatingCardText = greatingCardText;
	}


	public List<MediaFiles> getFiles() {
		return files;
	}

	public void setFiles(List<MediaFiles> files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "PostCard [postCardId=" + greatingCardId + ", postCardText=" + greatingCardText  
				+ ", files=" + files + "]";
	}



}
