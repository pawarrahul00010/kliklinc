package com.technohertz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.technohertz.model.UserOtp;

@EnableJpaRepositories("com.technohertz.repo")
public interface UserOtpRepository extends JpaRepository<UserOtp, Integer>,JpaSpecificationExecutor<UserOtp>{

	@Query(value="select r from UserOtp r where r.reg_Id=:reg_Id")
	UserOtp getOneByUserId(int reg_Id);
	
	/*
	 * @Query(
	 * value="update UserOtp r set r.otp=:otp, r.lastModifiedDate=:lastModifiedDate where r.reg_Id=:reg_Id"
	 * ) void updateOTPByUserId(int otp, LocalDateTime lastModifiedDate, int
	 * reg_Id);
	 */
}
