package com.technohertz.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.technohertz.model.UserOtp;
import com.technohertz.model.UserRegister;


public interface IUserOtpService {
	

	public UserOtp save(UserOtp userOtp);
	public List<UserOtp> saveMultiple(List<UserOtp> list);

	/* public void updateOTPByUserId(UserOtp userOtp); */
	public void deleteById(int reg_Id);
	public void update(UserOtp userOtp);
	public UserOtp getOneById(int userOtpId);
	public List<UserOtp> getAll();
	public Page<UserOtp> getAll(Specification<UserOtp> s,Pageable pageable);
	

}
