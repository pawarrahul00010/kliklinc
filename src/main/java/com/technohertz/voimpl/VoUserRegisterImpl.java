package com.technohertz.voimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.technohertz.model.UserRegister;
import com.technohertz.service.impl.UserRegisterServiceImpl;
import com.technohertz.util.DateUtil;
import com.technohertz.util.TockenUtil;
import com.technohertz.vo.VoUserRegister;

@Component
public class VoUserRegisterImpl {
	@Autowired
	UserRegisterServiceImpl userRegisterServiceImpl;
	
	@Autowired
	DateUtil dateUtil;

	@Autowired
	TockenUtil tockenUtil;
	public UserRegister convertToNoteEntity(VoUserRegister viewModel) {
		UserRegister entity = new UserRegister();
		//userRegisterServiceImpl.convertToNoteEntity(viewModel);
		
		entity.setEmail(viewModel.getEmail());
		entity.setIsActive(false);
		entity.setPassword(viewModel.getPassword());
		entity.setCreateDate(viewModel.getCreateDate());
		entity.setLastModifiedDate(viewModel.getLastModifiedDate());
		entity.setToken(viewModel.getToken());
		return entity;
	}
	
}
