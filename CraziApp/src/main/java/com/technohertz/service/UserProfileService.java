package com.technohertz.service;

import java.util.List;

import com.technohertz.model.UserRegister;

public interface UserProfileService {
	List<UserRegister> findByUserName(String userName);
}
