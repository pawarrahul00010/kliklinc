package com.technohertz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.technohertz.model.UserOtp;

@EnableJpaRepositories("com.technohertz.repo")
public interface UserContactRepository extends JpaRepository<UserOtp, Integer>,JpaSpecificationExecutor<UserOtp>{
	

}
