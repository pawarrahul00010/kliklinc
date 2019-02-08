package com.technohertz.service.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.technohertz.model.GroupProfile;
import com.technohertz.model.UserContact;
import com.technohertz.repo.GroupProfileRepository;
import com.technohertz.service.IGroupProfileService;

@Service
public class GroupProfileServiceImpl implements IGroupProfileService{
	
	
	@Autowired
	private GroupProfileRepository groupProfileRepository;
	
	@Autowired
	public EntityManager entityManager;



	public GroupProfile save(GroupProfile groupProfile) {
		
		return groupProfileRepository.save(groupProfile);
	}

	@Override
	public List<GroupProfile> findById(int groupId) {
		// TODO Auto-generated method stub
		return groupProfileRepository.findById(groupId);
	}

	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public List<GroupProfile> getUserGroupdetailByUserId(String contactNumber) {
		
		return entityManager.createNativeQuery("SELECT * FROM group_profile WHERE group_id IN"
				+ " ( SELECT " + 
				"	g.group_profile_group_id FROM " + 
				"	group_profile_group_member g  INNER JOIN group_profile u"
				+ " ON g.group_member_contact_id IN "
						+ "("
						+ " SELECT contact_id FROM user_contact WHERE contact_number=:contactNumber"
						+ ")"
				+ "GROUP BY g.group_profile_group_id"
				+ ")", GroupProfile.class)
				 .setParameter("contactNumber", contactNumber).getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getUserGroupsByUserId(String contactNumber) {
		// TODO Auto-generated method stub
		return entityManager.createNativeQuery(" SELECT " + 
													"	g.group_profile_group_id FROM " + 
													"	group_profile_group_member g  INNER JOIN group_profile u"
													+ " ON g.group_member_contact_id IN "
															+ "("
															+ " SELECT contact_id FROM user_contact WHERE contact_number=:contactNumber"
															+ ")"
													+ "GROUP BY g.group_profile_group_id")
				.setParameter("contactNumber", contactNumber).getResultList();
	}
}
