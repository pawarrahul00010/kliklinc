package com.technohertz.service.impl;



import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.technohertz.model.AdminRegister;
import com.technohertz.model.MediaFiles;
import com.technohertz.model.UserRegister;
import com.technohertz.repo.AdminRegisterRepository;
import com.technohertz.repo.MediaFileRepo;
import com.technohertz.repo.UserRegisterRepository;
import com.technohertz.service.IAdminRegisterService;

@Service
public class AdminRegisterServiceImpl implements IAdminRegisterService {

	@Autowired
	private AdminRegisterRepository adminRegisterRepo;
	@Autowired
	private MediaFileRepo mediaFileRepo;
	@Autowired
	private UserRegisterRepository userRegisterRepository;
	
	@Autowired
	public EntityManager entityManager;

	@Override 
	public AdminRegister save(AdminRegister user) { 
		user.setCreateDate(user.getLastModifiedDate()); 
		return adminRegisterRepo.save(user); 
	}

	@Override 
	public List<AdminRegister> saveMultiple(List<AdminRegister> list) {
		return adminRegisterRepo.saveAll(list);
		}

	@Override 
	public void update(AdminRegister user) { 
		user.setCreateDate(adminRegisterRepo.getOne(user.getAdminId()) .getCreateDate());
		user.setLastModifiedDate(user.getLastModifiedDate()); 
		adminRegisterRepo.save(user);
	}


	@Override 
	public List<AdminRegister> getAll() { 
		List<AdminRegister> list=adminRegisterRepo.findAll(); 

		return list; 
	}

	@Override 
	public Page<AdminRegister> getAll(Specification<AdminRegister> s, Pageable pageable)
		{ 
			Page<AdminRegister> page=adminRegisterRepo.findAll(s, pageable); 
			
			return page; 
		}

//	@Override
//	public List<UserRegister> findByUserNameAndPassword(String userName, String password) {
//	List<UserRegister> user=userRegisterRepo.findByUserNameAndPassword(userName, password);
//		return user;
//	}
@Transactional
	public int deleteUserById(@RequestParam("userId") Integer userId) {
		List<UserRegister> user = userRegisterRepository.getById(userId);
		
		if (!user.isEmpty()) {
			@SuppressWarnings("unused")
			
			int userid = entityManager.createNativeQuery("delete r,u,o,b from User_Register r INNER JOIN User_Profile u on r.userid=u.USR_DET_ID INNER JOIN user_otp o on r.userid=o.otp_id INNER JOIN biometric_table b ON r.userid=b.biometric_id where userid=:userId")
					.setParameter("userId", userId).executeUpdate();
	
		return userId;
	}
		return userId;
	}
@Transactional
public int deleteUserFile(@RequestParam("fileId") Integer fileId) {
	MediaFiles user = mediaFileRepo.getById(fileId);
	
	if (user!=null) {
		@SuppressWarnings("unused")
		
		int userid = entityManager.createNativeQuery("delete r from Media_Files r where file_id=:fileId")
				.setParameter("fileId", fileId).executeUpdate();

	return fileId;
}
	return 0;
}
	@Override
	public List<AdminRegister> findByMailId(String mailId) {
		List<AdminRegister> userList=adminRegisterRepo.findByMailId(mailId);
		return userList;
	}


	@Override
	public List<AdminRegister> getById(Integer userId) {
		List<AdminRegister> idList=adminRegisterRepo.getById(userId);
		return idList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllMobile() {
		
		return entityManager.createQuery("SELECT r.mobilNumber from UserRegister r ")
				.getResultList();

	}


}
