package com.technohertz.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.technohertz.model.UserOtp;
import com.technohertz.model.UserRegister;
import com.technohertz.repo.UserOtpRepository;
import com.technohertz.repo.UserRegisterRepository;
import com.technohertz.service.IUserOtpService;

@Service
public class UserOtpServiceImpl implements IUserOtpService {

	@Autowired
	private UserOtpRepository userOtpRepo;
	
	@Autowired
	private UserRegisterRepository userRegisterRepo;


	@Override 
	public UserOtp save(UserOtp userOtp) { 
		userOtp.setCreateDate(userOtp.getLastModifiedDate()); 
		return userOtpRepo.save(userOtp); 
	}

	@Override 
	public List<UserOtp> saveMultiple(List<UserOtp> list) {
		return userOtpRepo.saveAll(list);
		}

	@Override 
	public void update(UserOtp userOtp) { 
		userOtp.setLastModifiedDate(userOtp.getLastModifiedDate());
		userOtp.setCreateDate(userOtp.getCreateDate());
		userOtpRepo.save(userOtp);
	}
	


	@Override 
	public void deleteById(int reg_Id) {
		if(userOtpRepo.existsById(userRegisterRepo.getOne(reg_Id).getUserOtp().getOtpId()))
			{ 
			userOtpRepo.deleteById(userRegisterRepo.getOne(reg_Id).getUserOtp().getOtpId()); 
			} 
		}

	@Override 
	public UserOtp getOneById(int reg_Id) {
		
		return userOtpRepo.getOne(userRegisterRepo.getOne(reg_Id).getUserOtp().getOtpId());
		
	}

	@Override 
	public List<UserOtp> getAll() { 
		List<UserOtp> list=userOtpRepo.findAll(); 

		return list; 
	}

	@Override 
	public Page<UserOtp> getAll(Specification<UserOtp> s, Pageable pageable)
		{ 
			Page<UserOtp> page=userOtpRepo.findAll(s, pageable); 
			
			return page; 
		}


	/*
	 * @Override public void updateOTPByUserId(UserOtp userOtp) {
	 * 
	 * userOtpRepo.updateOTPByUserId(userOtp.getOtp(),
	 * userOtp.getLastModifiedDate(), userOtp.getReg_Id());
	 * 
	 * }
	 */

	



}
