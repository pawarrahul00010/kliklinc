package com.technohertz.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.technohertz.model.GroupProfile;

@Repository
public interface GroupProfileRepository extends JpaRepository<GroupProfile, Integer>{

	Object findBydisplayName(String displayName);
	List<GroupProfile> getByDisplayName(String userName);
	@Query(value="SELECT  r from GroupProfile r WHERE r.profileId=?1")
	List<GroupProfile> findById(int id);

}
