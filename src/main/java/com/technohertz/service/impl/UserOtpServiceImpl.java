package com.technohertz.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.technohertz.model.UserOtp;
import com.technohertz.repo.UserOtpRepository;
import com.technohertz.service.IUserOtpService;

@Service
public class UserOtpServiceImpl implements IUserOtpService {

	@Autowired
	private UserOtpRepository userOtpRepo;


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
		userOtp.setCreateDate(userOtpRepo.getOne(userOtp.getUserId()) .getCreateDate());
		userOtp.setLastModifiedDate(userOtp.getLastModifiedDate()); 
		userOtpRepo.save(userOtp);
	}

	@Override 
	public void deleteById(int userId) {
		if(userOtpRepo.existsById(userId))
			{ 
				userOtpRepo.deleteById(userId); 
			} 
		}

	@Override 
	public UserOtp getOneById(int userOtpId) { return
			userOtpRepo.getOne(userOtpId); }

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




}
