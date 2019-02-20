package com.technohertz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technohertz.model.GroupProfile;

@Repository
public interface GroupProfileRepository extends JpaRepository<GroupProfile, Integer>{

	

}
