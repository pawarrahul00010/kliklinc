package com.technohertz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technohertz.model.AdminProfile;

@Repository
public interface AdminProfileRepository extends JpaRepository<AdminProfile, Integer>{


}
