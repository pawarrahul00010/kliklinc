package com.technohertz.service.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.technohertz.common.Constant;
import com.technohertz.model.LikedUsers;
import com.technohertz.repo.UserProfileRepository;
import com.technohertz.service.IMediaFileService;

@Service
public class MediaFileServiceImpl implements IMediaFileService{
	
	@Autowired
	private UserProfileRepository userprofilerepo;

	@Autowired
	public EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<LikedUsers> getUserLikesByFileId(int fileid, String userName) {

		return entityManager.createNativeQuery("SELECT * FROM liked_users WHERE file_id IN (:fileid) AND user_name=:user_name AND TYPE IN(:type1,:type2)",LikedUsers.class)
				.setParameter("fileid", fileid)
				.setParameter("type1", Constant.LIKE)
				.setParameter("type2", Constant.UNLIKED)
				.setParameter("user_name", userName).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LikedUsers> getUserRatingByFileId(String userfileid, int userId) {
		
		return entityManager.createNativeQuery("SELECT * FROM liked_users WHERE file_id IN (:fileid) AND user_id=:userId AND TYPE IN(:type)",LikedUsers.class)
				.setParameter("fileid", userfileid)
				.setParameter("type", Constant.RATE)
				.setParameter("userId", userId).getResultList();
	}


}
