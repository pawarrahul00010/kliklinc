package com.technohertz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.technohertz.model.UserOtp;

@EnableJpaRepositories("com.technohertz.repo")
public interface UserOtpRepository extends JpaRepository<UserOtp, Integer>,JpaSpecificationExecutor<UserOtp>{

}
