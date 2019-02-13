package com.technohertz.service;

import java.util.List;

import com.technohertz.model.LikedUsers;

public interface IMediaFileService {
	
	
	List<LikedUsers> getUserLikesByFileId(int fileid, String userName);

	List<LikedUsers> getUserRatingByFileId(String userfileid, int userId);
	
}
