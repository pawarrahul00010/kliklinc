package com.technohertz.service;

import java.util.List;

import com.technohertz.model.GroupProfile;
import com.technohertz.model.UserContact;

public interface IGroupProfileService {
	
	public GroupProfile save(GroupProfile groupProfile);

	public List<GroupProfile> findById(int groupId);
	
	List<GroupProfile> getUserGroupdetailByUserId(String userId);

	List<Integer> getUserGroupsByUserId(String userId);

}
