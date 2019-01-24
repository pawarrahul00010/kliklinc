package com.technohertz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technohertz.model.UserProfile;
import com.technohertz.model.UserRegister;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer>{

	Object findBydisplayName(String displayName);
	

}
