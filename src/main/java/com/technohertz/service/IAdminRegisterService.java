package com.technohertz.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.technohertz.model.AdminRegister;


public interface IAdminRegisterService {
	

	public AdminRegister save(AdminRegister user);
	public List<AdminRegister> saveMultiple(List<AdminRegister> list);
	public void update(AdminRegister user);
	public int deleteUserById(Integer userID);
	public List<AdminRegister> getAll();
	public Page<AdminRegister> getAll(Specification<AdminRegister> s,Pageable pageable);
	public int deleteUserFile(Integer fileId);
	public List<AdminRegister> getById(Integer adminId);

	
	List<String> getAllMobile();
	
	List<AdminRegister> findByMailId(String mailId);


	

}
